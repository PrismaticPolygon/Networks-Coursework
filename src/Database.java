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

        for (String a : this.songs.keySet()) {

            if (calculateLevenshteinDistance(a, artist) <= 2) {

                return "Did you mean " + a + "?";

            }

        }

        return "'" + artist + "'" + " not found";

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

    private int calculateLevenshteinDistance(String x, String y) {

        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];

    }

    private int costOfSubstitution(char a, char b) {

        return a == b ? 0 : 1;

    }

    private int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

}

// https://www.baeldung.com/java-levenshtein-distance
