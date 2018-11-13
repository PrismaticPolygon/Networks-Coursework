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

        if (args.length == 2) {

            try {

                Client client = new Client(args[0], Integer.parseInt(args[1]));

            } catch (NumberFormatException e) {

                System.out.println("Invalid port number: " + args[1]);

            }

        } else {

            System.out.println("Invalid arguments");

        }

    }

    public Client(String hostname, int port) {

        try (

            Socket socket = new Socket(hostname, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        ) {

            System.out.println("Connection successful");

            String userInput, response;

            while (true) {

                System.out.print("\nEnter an artist: ");

                userInput = stdIn.readLine();

                if (userInput.equals("quit")) {

                    break;

                }

                long requestStart = System.currentTimeMillis();

                out.println(userInput);
                response = in.readLine();

                System.out.println("Response: " + response);

                this.logger.log("Received response ("  + (System.currentTimeMillis() - requestStart) +
                        " ms): " + "'" + response +  "' (" + response.getBytes().length + " bytes) " +
                        "for request " + "'" + userInput + "'");

            }

        } catch (ConnectException e) {

            System.out.println("Connection refused");
            System.exit(1);

        } catch (UnknownHostException e) {

            System.out.println("Don't know about host: " + hostname);
            System.exit(1);

        } catch (IOException e) {

            System.out.println("Couldn't get I/O for connection to " + hostname);
            System.exit(1);

        } finally {

            System.out.println("\nConnection closed"); // Though I don't explicitly check, the try-with-resources statement is guaranteed to close the connection itself.
            this.logger.toFile();

        }

    }

}

// TODO: multithreading
// TODO: improve error-handling; test cases

// https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html
