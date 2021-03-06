import java.io.BufferedWriter;
import java.io.File;
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

    /**
     * Prepend the datetime to the string param and store it in the logs member variable
     * @param string
     */
    public void log(String string) {

        String datetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

        this.logs.add(datetime + ": " + string + "\n");

    }

    /**
     * Write the log to disk, using the filename member variable
     */
    public void toFile() {

        String directoryName = "./logs";

        File directory = new File(directoryName);

        if (! directory.exists()) {

            directory.mkdir();

        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryName + "/" + filename))) {

            for (String s : this.logs) {

                writer.write(s);

            }

        } catch (IOException e) {

            System.err.println("Error writing logs: " + e.toString());

        }

    }

}
