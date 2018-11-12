import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    public static void main(String[] args) {

        Client client = new Client(args[0]);

    }

    public Client(String portString) {

        Integer portNumber = Integer.parseInt(portString);

        try (

            Socket socket = new Socket("127.0.0.1", portNumber);
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            DataInputStream is = new DataInputStream(socket.getInputStream())

        ) {

            try {

                os.writeBytes("HELLO\n");

                String responseLine;

                while ((responseLine = is.readLine()) != null) {

                    System.out.println("Server: " + responseLine);

                    if (responseLine.contains("Ok")) {

                        break;

                    }

                }

            } catch (UnknownHostException e) {

                System.err.println("Trying to connect to an unknown host: " + e);

            } catch (IOException e) {

                System.err.println("IOException : " + e);


            }


        } catch (UnknownHostException e) {

            System.out.println("Don't know about host: hostname");

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}

// https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html
