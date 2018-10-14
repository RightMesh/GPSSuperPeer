package repository;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;

public interface LatestDocumentCallback {
    void latest(ChangeStreamDocument<Document> document);
}
