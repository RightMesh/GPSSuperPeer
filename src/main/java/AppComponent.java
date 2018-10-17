import dagger.Component;
import io.left.rightmesh.mesh.MeshManager;
import repository.Repository;

import javax.inject.Singleton;

@Singleton
@Component(modules = {SuperPeerModule.class})
public interface AppComponent {
    void inject(SuperPeer superPeer);
    Repository buildRepository();
    MeshManager buildMeshManager();
}
