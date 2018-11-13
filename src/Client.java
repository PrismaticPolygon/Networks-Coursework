import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    private Logger logger = new Logger("client.log");

    public static void main(String[] args) {

        Client client = new Client(args[0]);

    }

    // Handle the case where: the server is not running / available
    // The port number is busy (?)
    // User did not input an artist name
    // Server response is not received.

    // No kind of error checking.

    // After the client has sent a request, received and processed that response, it should prompt to quit.


    public Client(String portString) {

        Integer portNumber = Integer.parseInt(portString);

        try (

            Socket socket = new Socket("127.0.0.1", portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))

        ) {

            String userInput;

            while ((userInput = stdIn.readLine()) != null && !userInput.equals("exit") && !userInput.equals("quit")) {

                long requestStart = System.currentTimeMillis();

                out.println(userInput);

                String response = in.readLine();

                System.out.println("Server says: " + response);




                this.logger.log("Server took "  + (System.currentTimeMillis() - requestStart) +
                        "ms to respond with " + "'" + response +  "' (" + response.getBytes().length + " bytes) " +
                        "for the request " + "'" + userInput + "'");

            }

        } catch (ConnectException e) {

            System.out.println("Connection refused");
            System.exit(1);

        } catch (UnknownHostException e) {

            System.out.println("Don't know about host: 127.0.0.1");
            System.exit(1);

        } catch (IOException e) {

            System.out.println("Couldn't get I/O for connection to 127.0.0.1");
            System.exit(1);

        } finally {

            this.logger.toFile();

        }

    }

}

// https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html
