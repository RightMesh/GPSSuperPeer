package repository;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.FullDocument;
import model.Latitude;
import model.Longitude;
import org.bson.Document;

import java.util.Collections;
import java.util.Date;
import java.util.logging.Logger;

import static java.time.Instant.now;
import static java.util.Arrays.asList;

public class MongoRepository implements Repository {
    private final static Logger LOGGER = Logger.getLogger(MongoRepository.class.getName());
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
    public void insert(Latitude latitude, Longitude longitude) {
        Date now = Date.from(now());
        collection.insertOne(new Document("timestamp", now.toString())
                .append("lat", latitude.asString())
                .append("long", longitude.asString()), (result, t) -> {
            LOGGER.info("Inserted update at: " + now.toString());
        });
    }
}
