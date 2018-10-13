import ether.TransactionsManager;
import io.left.rightmesh.mesh.JavaMeshManager;
import io.left.rightmesh.mesh.MeshManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SuperPeer {
    //TODO: Add logger instead of system.out
    public static final String TAG = SuperPeer.class.getCanonicalName();

    private static final String EXIT_CMD = "exit";
    private static final String CLOSE_CHANNEL_CMD = "close";

    JavaMeshManager mm;
    private boolean isRunning = true;
    private TransactionsManager tm;

    public static void main(String[] args) {
        if (args.length > 0 && (args[0].equals("-h") || args[0].equals("--headless"))) {
            // Doesn't read from STDIN if run with `-h` or `--headless`.
            // Useful for backgrounding.
            SuperPeer serviceMode = new SuperPeer(false);
        } else {
            // Default condition, runs in interactive mode.
            SuperPeer interactiveMode = new SuperPeer(true);
        }
    }

    public SuperPeer(boolean interactive) {
        mm = new JavaMeshManager(true);

        System.out.println("Superpeer MeshID: " + mm.getUuid());
        System.out.println("Superpeer is waiting for library ... ");
        try {
            Thread.sleep(200);

        } catch (InterruptedException ignored) { }


        tm = TransactionsManager.getInstance(mm);
        if (tm == null){
            System.out.println("Failed to get TransactionManager from library. Superpeer is shutting down ...");
            mm.stop();
            System.exit(0);
        }
        tm.start();
        initHooks();

        System.out.println("Superpeer is ready!");

        if (interactive) {
            // Block for user input if running in interactive mode.
            String msg;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            do {
                try {
                    msg = br.readLine();
                    processInput(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            } while (isRunning);

            // Clean up and exit when exit command is entered.
            finish();
            System.exit(0);
        } else {
            // Infinitely loop if run in quiet mode.
            while (true) { /* Loop until killed. */ }
        }
    }

    private void initHooks() {
        // Stop everything when runtime is killed.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SuperPeer.this.finish();
        }));

        mm.on(MeshManager.DATA_RECEIVED, (event) ->
                // TODO: store in DB
                System.out.println("Data received: " + event.toString()));

        mm.on(MeshManager.DATA_DELIVERED, (event) ->
                System.out.println("Data delivered: " + event.toString()));
    }

    /**
     * Default to interactive mode.
     */
    public SuperPeer() {
        this(true);
    }

    /**
     * Shut down mesh functionality cleanly. Must be run on exit or port will remain bound.
     */
    private void finish() {
        tm.stop();
        mm.stop();
    }

    private void processInput(String msg) {
        if(msg.equals(EXIT_CMD)) {
            isRunning = false;
            return;
        }

        String[] args = msg.split(" ");
        if(args.length == 0) {
            return;
        }

        switch (args[0]) {
            case CLOSE_CHANNEL_CMD:
                processCloseCmd(args);
                break;

            default:
                System.out.println("Invalid command.");
                break;
        }
    }

    private void processCloseCmd(String[] args) {
        if(args.length != 2) {
            System.out.println("Invalid args.");
            return;
        }

        tm.closeChannels(args[1]);
    }
}