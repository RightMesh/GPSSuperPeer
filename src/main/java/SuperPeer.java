/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import io.left.rightmesh.id.MeshID;
import io.left.rightmesh.mesh.JavaMeshManager;
import io.left.rightmesh.mesh.MeshManager;
import io.left.rightmesh.mesh.MeshManager.MeshTransactionEvent;
import io.left.rightmesh.util.MeshUtility;

import java.io.Console;

import static io.left.rightmesh.mesh.MeshManager.TRANSACTION_RECEIVED;
 
public class SuperPeer {

    public static final String TAG = SuperPeer.class.getCanonicalName();
        
    public static void main(String[] args) {
        SuperPeer p = new SuperPeer();
    }
    
    public SuperPeer() {
        JavaMeshManager mm = new JavaMeshManager(true);
        mm.on(TRANSACTION_RECEIVED, this::handleTransactionPacket);
        System.out.println("Superpeer MeshID: " + mm.getUuid());
        
        System.out.println("Waiting for lib to be ready");
        try {
                Thread.sleep(200);
        } catch(Exception ex) {}
        
        //test send on the TRANSACTION PORT
        mm.sendDataReliable(mm.getUuid(), MeshUtility.TRANSACTION_PORT, "test".getBytes());
        
        //Continue to run until user quits
        System.out.println("Enter empty line to quit");
        Console c = System.console();
        String msg;
        if (c == null) {
            System.err.println("No console.");
            mm.stop();
            return;
        }
        
        do {
            msg = c.readLine();
        } while(msg != null && !msg.equals(""));
        
        mm.stop();
    }
    
    public void handleTransactionPacket(MeshManager.RightMeshEvent e) {
            System.out.println("TRANSACTION READY TO ROCK!");
    }  
}
