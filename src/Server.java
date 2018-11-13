import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class Server {

    private Database db = new Database();
    private Logger logger = new Logger("server.log");
    private int workerCount = 0;

    public static void main(String[] args) {

        try {

            Server server = new Server(Integer.parseInt(args[0]));

        } catch (NumberFormatException e) {

            System.out.println("Invalid port number: " + args[0]);

        }

    }

    public Server(int port) {

        long serverStart = System.currentTimeMillis();

        System.out.println("Server started on port " + port + "\n");
        this.logger.log("Server started on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            Socket clientSocket;

            while ((clientSocket = serverSocket.accept()) != null) {

                Thread thread = new Thread(new ClientWorker(clientSocket));
                thread.start();

                this.workerCount++;

            }

        } catch (IOException e) {

            System.err.println("Error starting server on port " + port + ": " + e.toString());
            this.logger.log("Error starting server on port " + port + ": " + e.toString());

        } finally {

            System.out.println("Server shut down (uptime: " + (System.currentTimeMillis() - serverStart) + " ms)");
            this.logger.log("Server shut down (uptime: " + (System.currentTimeMillis() - serverStart) + " ms)");
            this.logger.toFile();

        }

    }

    public class ClientWorker implements Runnable {

        private Socket client;
        private int workerNo;

        ClientWorker(Socket client) {

            this.client = client;
            this.workerNo = workerCount;

        }

        public void run() {

            long connectionStart = -1;

            try (

                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))

            ) {

                logger.log("Client " + workerNo + "successfully connected");

                connectionStart = System.currentTimeMillis();
                String userInput;

                while ((userInput = in.readLine()) != null) {

                    System.out.println("Request (client " + workerNo + "): " + userInput);

                    logger.log("Received request (client " + workerNo + "): '" + userInput + "'");

                    out.println(db.getSongs(userInput));

                }

            } catch (SocketException e) {

                System.out.println("Client " + workerNo + " reset connection");
                logger.log("Client " + workerNo + " reset connection");

                System.exit(1);

            } catch (IOException e) {

                System.out.println("Server error: " + e.toString());
                logger.log("Server error: " + e.toString());

            } finally {

                if (connectionStart != -1) {

                    logger.log("Client " + workerNo + " closed connection (duration: " +
                            (System.currentTimeMillis() - connectionStart) + " ms)");

                }

            }

        }

    }

}

// https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html?page=2
