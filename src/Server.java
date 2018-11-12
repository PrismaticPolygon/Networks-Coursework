import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;


public class Server {

    Database db = new Database();
    Logger log = new Logger("server.log");

    public static void main(String[] args) {

        Server server = new Server(args[0]);

    }

    public Server(String portString) {

        this.log.log("Server started");

        Integer portNumber = Integer.parseInt(portString);

        try (

                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))

        ) {

            out.print("Enter a artist: ");

            boolean done = false;

            while(!done) {

                String line = in.readLine();

                // How to inform the client that I received the request successfully?

                if(line.toLowerCase().trim().equals("peace")) {

                    done = true;

                }
            }

        } catch (IOException e) {

            System.out.println("Error creating server");

            e.printStackTrace();

        }

    }

}

// https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html?page=2
