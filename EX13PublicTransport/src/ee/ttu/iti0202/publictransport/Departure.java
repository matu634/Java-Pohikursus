package ee.ttu.iti0202.publictransport;

import java.time.Duration;
import java.time.LocalDateTime;

public class Departure implements Comparable<Departure> {
    private String name;
    private String time;
    private String destination;
    private static final int MINUTES_IN_HOUR = 60;

    public int getMinutesFromNow() {
        //if (LocalDateTime.now().isAfter(getTime())) return 0;
        Duration duration = Duration.between(LocalDateTime.now(), getTime());
        //TODO : duration.toDaysPart() * 1440 -> int + return
        return duration.toHoursPart() * MINUTES_IN_HOUR + duration.toMinutesPart();
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return LocalDateTime.parse(time.replace("Z", ""));
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return String.format("\n{Bussinumber: %s\nVäljub %s (%d min pärast)\n Sihtpunkt: %s}",
                name, time, getMinutesFromNow(), destination);
    }

    @Override
    public int compareTo(Departure o) {
        return Integer.compare(getMinutesFromNow(), o.getMinutesFromNow());
    }
}
