package repository;

import model.Latitude;
import model.Longitude;

public interface Repository {
    void registerUpdateCallbacks(SingleResultCallback singleResultCallback,
                                 LatestDocumentCallback latestDocumentCallback);
    void insert(Latitude latitude, Longitude longitude);
}
