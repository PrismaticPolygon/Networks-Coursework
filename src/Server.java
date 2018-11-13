import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Server {

    private Database db = new Database();
    private Logger logger = new Logger("server.log");

    public static void main(String[] args) {

        Server server = new Server(args[0]);

    }

    public Server(String portString) {

        this.logger.log("Server started on port " + portString);

        Integer portNumber = Integer.parseInt(portString);
        long connectionStart = -1;

        try (

                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))

        ) {

            connectionStart = System.currentTimeMillis();
            String userInput;

            while ((userInput = in.readLine()) != null) {

                System.out.println("Client says: " + userInput);

                this.logger.log("Received artist name: " + userInput);

                out.println(db.getSongs(userInput.toLowerCase()));

            }

        } catch (SocketException e) {

            System.out.println("Client reset connection");
            System.exit(1);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (connectionStart != -1) {

                this.logger.logConnectionTime(connectionStart);

            }

            logger.toFile();

        }

    }

}

// https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html?page=2
