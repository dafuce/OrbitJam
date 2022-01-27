import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Record implements Comparable<Record> {
    int score;
    int kills;
    String playername;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
    String date;

    Record(){
        Date temp = new Date(System.currentTimeMillis());
        date = sdf.format(temp);
    }
    public int compareTo(Record other) {
        if ( this.score == other.score) {
            return other.kills-this.kills;
        }
        return other.score-this.score;
    }
}
