import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public Server() {

        this.readFile();

    }




}

 // Can't even do it ordered, can we?

// Once the server starts, it is passed a
