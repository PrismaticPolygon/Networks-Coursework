import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private Map<String, ArrayList<String>> songs = new HashMap<>();

    public Database() {

        this.loadData();

    }

    public String getSongs(String artist) {

        for (String a : this.songs.keySet()) {

            if (a.contains(artist)) {

                ArrayList<String> songs = this.songs.get(a);

                if (songs.size() == 0) {

                    return "No songs associated with " + artist;

                } else {

                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < songs.size(); i++) {

                        sb.append(songs.get(i));

                        if (i != songs.size() - 1) {

                            sb.append(", ");

                        }

                    }

                    return sb.toString();

                }

            }

        }

        return artist + " not found";

    }

    private void loadData() {

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

}
