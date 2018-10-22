package repository;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.FullDocument;
import io.left.rightmesh.id.MeshId;
import model.Latitude;
import model.Longitude;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Date;

import static java.time.Instant.now;
import static java.util.Arrays.asList;

public class MongoRepository implements Repository {
    private final static Logger logger = LoggerFactory.getLogger(MongoRepository.class.getName());
    private MongoCollection<Document> collection;

    public MongoRepository() {
        // TODO: connection details should be configurable
        MongoClient mongoClient = MongoClients.create("mongodb://localhost");
        MongoDatabase database = mongoClient.getDatabase("locations");
        collection = database.getCollection("latLong");
    }

    @Override
    public void registerUpdateCallbacks(SingleResultCallback singleResultCallback,
                                        LatestDocumentCallback latestDocumentCallback) {
        collection.watch(Collections.singletonList(
                Aggregates.match(
                        Filters.in("operationType", asList("insert", "update", "replace", "delete")))))
                .fullDocument(FullDocument.UPDATE_LOOKUP)
                .forEach(latestDocumentCallback::latest,
                        (result, throwable) -> {
                            singleResultCallback.onResult();
                        });
    }

    @Override
    public void insert(MeshId remoteMeshId, Latitude latitude, Longitude longitude, MeshId superPeerMeshId) {
        Date now = Date.from(now());
        collection.insertOne(new Document("timestamp", now.toString())
                .append("remoteMeshId", remoteMeshId.toString())
                .append("superPeerMeshId", superPeerMeshId.toString())
                .append("lat", latitude.asDouble())
                .append("long", longitude.asDouble()), (result, t) -> {
            logger.info("Inserted update at: " + now.toString());
        });
    }
}
