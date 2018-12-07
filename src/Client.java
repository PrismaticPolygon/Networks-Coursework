import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Logger logger = new Logger("client.log");

    public static void main(String[] args) {

        if (args.length >= 2) {

            try {

                Client client = new Client(args[0], Integer.parseInt(args[1]));

            } catch (NumberFormatException e) {

                Client client = new Client("127.0.0.1", 4444);

            }

        } else {

            Client client = new Client("127.0.0.1", 4444);

        }

    }

    /**
     * Constructor for the client class. Logic inside.
     * @param hostname
     * @param port
     */
    public Client(String hostname, int port) {

        System.out.println("Client started");
        this.logger.log("Client started");

        try (

            Socket socket = new Socket(hostname, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        ) {

            System.out.println("Successfully connected to server on " + hostname + ":" + port);
            this.logger.log("Successfully connected to server on " + hostname + ":" + port);


            String userInput, response;

            while (true) {

                System.out.print("\nEnter an artist: ");

                userInput = stdIn.readLine();

                if (userInput.equals("quit")) {

                    break;

                }

                if (!userInput.equals("")) {

                    long requestStart = System.currentTimeMillis();

                    out.println(userInput);
                    response = in.readLine();

                    System.out.println("Response: " + response);

                    this.logger.log("Received response ("  + (System.currentTimeMillis() - requestStart) +
                            " ms): " + "'" + response +  "' (" + response.getBytes().length + " bytes) " +
                            "for request " + "'" + userInput + "'");

                }

                System.out.print("\nQuit? (Y/N): ");

                userInput = stdIn.readLine();

                if (userInput.toLowerCase().equals("y")) {

                    break;

                }

            }

        } catch (ConnectException e) {

            System.out.println("\nConnection to " + hostname + " refused");
            this.logger.log("Connection to " + hostname + " refused");

        } catch (UnknownHostException e) {

            System.out.println("\nDon't know about host: " + hostname);
            this.logger.log("Don't know about host: " + hostname);

        } catch (IOException e) {

            System.out.println("\nCouldn't get I/O for connection to " + hostname);
            this.logger.log("Couldn't get I/O for connection to " + hostname);

        } finally {

            System.out.println("\nConnection closed"); // Though I don't explicitly check, the try-with-resources statement is guaranteed to close the connection itself.
            this.logger.log("Connection closed");
            this.logger.toFile();

        }

    }

}

// https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html
