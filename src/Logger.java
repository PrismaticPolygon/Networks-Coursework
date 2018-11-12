import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

    private StringBuilder sb = new StringBuilder();
    private String filename;

    public Logger(String filename) {

        this.filename = filename;

    }

    public void log(String string) {

        this.sb.append(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
        this.sb.append(string);
        this.sb.append("\n");

    }

    public void toFile() {

        System.out.println(this.filename);
        System.out.println(this.sb.toString());

    }

}
