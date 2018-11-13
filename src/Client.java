import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    public static void main(String[] args) {

        Client client = new Client(args[0]);

    }

    // Handle the case where: the server is not running / available
    // The port number is busy (?)
    // User did not input an artist name
    // Server response is not received.

    // No kind of error checking.

    public Client(String portString) {

        Integer portNumber = Integer.parseInt(portString);

        try (

            Socket socket = new Socket("127.0.0.1", portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))

        ) {

            String userInput;

            while ((userInput = stdIn.readLine()) != null && !userInput.equals("exit")) {

                out.println(userInput);

                // Gets held up here, I guess. Loop presumably passes by before response?
                // No, Java is synchronous.

                String response = in.readLine();

                System.out.println("Server says: " + response);

            }


//            try {
//
//                out.write("Debby Boone");
//
//                while (true) {
//
//                    String responseLine = in.readLine();
//
//                    if (responseLine != null) {
//
//                        System.out.println("Server says: " + responseLine);
//                    }
//
//                }
//
//            } catch (UnknownHostException e) {
//
//                System.err.println("Trying to connect to an unknown host: " + e);
//
//            } catch (IOException e) {
//
//                System.err.println("IOException : " + e);
//
//
//            }


        } catch (ConnectException e) {

            System.out.println("Connection refused");
            System.exit(1);

        } catch (UnknownHostException e) {

            System.out.println("Don't know about host: 127.0.0.1");
            System.exit(1);

        } catch (IOException e) {

            System.out.println("Couldn't get I/O for connection to 127.0.0.1");
            System.exit(1);

        }

    }

}

// https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html
