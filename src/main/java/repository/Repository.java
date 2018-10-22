package repository;

import io.left.rightmesh.id.MeshId;
import model.Latitude;
import model.Longitude;

public interface Repository {
    void registerUpdateCallbacks(SingleResultCallback singleResultCallback,
                                 LatestDocumentCallback latestDocumentCallback);
    void insert(MeshId remoteMeshId, Latitude latitude, Longitude longitude);
}
