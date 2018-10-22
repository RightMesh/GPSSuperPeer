package superPeer;

import io.left.rightmesh.mesh.MeshManager;
import io.left.rightmesh.util.RightMeshException;
import model.Latitude;
import model.Longitude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.Repository;
import util.BytesToDouble;

import javax.inject.Inject;

public class SuperPeer {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    Repository repository;
    @Inject
    MeshManager meshManager;

    private SuperPeer() {
        DaggerAppComponent
                .builder()
                .build()
                .inject(this);

        logger.info("Superpeer MeshID: " + meshManager.getUuid());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                meshManager.stop();
            } catch (RightMeshException e) {
                e.printStackTrace();
            }
        }));

        meshManager.on(MeshManager.DATA_RECEIVED, (event) -> {
            MeshManager.DataReceivedEvent dataEvent = (MeshManager.DataReceivedEvent) event;
            assert dataEvent.data.length == Double.SIZE * 2;
            double[] latLongValues = BytesToDouble.convertMany(dataEvent.data, 2);

            repository.insert(
                    event.peerUuid,
                    Latitude.of(latLongValues[0]),
                    Longitude.of(latLongValues[1]),
                    meshManager.getUuid()
            );

            logger.info("Data received: " + dataEvent.toString());
        });


        meshManager.on(MeshManager.DATA_DELIVERED, (event) ->
                logger.info("Data delivered: " + event.toString()));
    }

    public static void main(String[] args) {
        new SuperPeer();

        for (; ;) {
            // run
        }
    }
}
