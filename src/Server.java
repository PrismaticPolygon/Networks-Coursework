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

    public static void main(String[] args) {

        if (args.length >= 1) {

            try {

                Server server = new Server(Integer.parseInt(args[0]));

            } catch (NumberFormatException e) {

                System.out.println("Invalid port number: " + args[0]);

            }

        } else {

            System.out.println("Too few arguments");

        }

    }

    public Server(int port) {

        long connectionStart = -1;
        System.out.println("Server started on port " + port + "\n");
        this.logger.log("Server started on port " + port);

        try (

                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))

        ) {

            logger.log("Client successfully connected");

            connectionStart = System.currentTimeMillis();

            String userInput;

            while ((userInput = in.readLine()) != null) {

                System.out.println("Request: " + userInput);

                logger.log("Received request: '" + userInput + "'");

                out.println(db.getSongs(userInput));

            }

        } catch (SocketException e) {

            System.out.println("Client reset connection");
            logger.log("Client reset connection");

            System.exit(1);

        } catch (IOException e) {

            System.out.println("Server error: " + e.toString());
            logger.log("Server error: " + e.toString());

        } finally {

            if (connectionStart != -1) {

                System.out.println("\nClient closed connection (duration: " + (System.currentTimeMillis() - connectionStart) + " ms)");
                logger.log("Client closed connection (duration: " + (System.currentTimeMillis() - connectionStart) + " ms)");

            }

            logger.toFile();

        }

    }

}

// https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html?page=2
