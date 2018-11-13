import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Logger {

    private ArrayList<String> logs = new ArrayList<>();
    private String filename;

    public Logger(String filename) {

        this.filename = filename;

    }

    public void log(String string) {

        String datetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

        this.logs.add(datetime + ": " + string + "\n");

    }

    public void toFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("../logs/" + this.filename))) {

            for (String s : this.logs) {

                writer.write(s);

            }

        } catch (IOException e) {

            System.err.println("Error writing logs: " + e.toString());

        }

    }

}
