import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Server {

    private Map<String, ArrayList<String>> songs = new HashMap<>();

    private void readFile() {

        try (BufferedReader br = new BufferedReader(new FileReader("D:/Dev/IdeaProjects/Networks-Coursework/songs.txt"))) {

            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {

                if (i >= 6 && i <= 111) {

                    String artist, title;

                    if (!Character.isDigit(line.charAt(line.length() - 1))) {

                        title = line.substring(4).trim();
                        line = br.readLine();

                        i++;

                    } else {

                        title = line.substring(4, 35).trim();

                    }

                    artist = line.substring(35, line.length() - 4).trim();

                    if (songs.containsKey(artist)) {

                        songs.get(artist).add(title);

                    } else {

                        songs.put(artist, new ArrayList<>(Arrays.asList(title)));

                    }

                }

                i++;

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public Server(String[] args) {

        System.out.println("Creating server");

        int portNumber = Integer.parseInt(args[0]);

        try (

                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))

        ) {

            System.out.println("Running server");

            out.println("Hello world! Enter peace to exit!");

            boolean done = false;

            while(!done) {

                String line = in.readLine();
                out.println("Echo from <Your Name Here> Server: " + line);

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
