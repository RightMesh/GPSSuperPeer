import dagger.Module;
import dagger.Provides;
import io.left.rightmesh.mesh.JavaMeshManager;
import io.left.rightmesh.mesh.MeshManager;
import repository.MongoRepository;
import repository.Repository;

import javax.inject.Singleton;

@Module
class SuperPeerModule {

    @Provides
    @Singleton
    Repository provideRepository() {
        return new MongoRepository();
    }

    @Provides
    @Singleton
    MeshManager provideMeshManager() {
        return new JavaMeshManager(true);
    }
}

