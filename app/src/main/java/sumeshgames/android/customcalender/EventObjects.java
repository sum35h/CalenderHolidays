package sumeshgames.android.customcalender;

import java.util.Calendar;
import java.util.Date;
public class EventObjects {
    private int id;
    private String message;
    private Date date;
    public EventObjects(String message, Date date) {
        this.message = message;
        this.date = date;
    }
    public EventObjects(int id, String message, Date date) {
        this.date = date;
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }
    public Date getDate() {
        return date;
    }
    public String getDay()
    {
        Calendar eventCalendar = Calendar.getInstance();
        eventCalendar.setTime(date);
        return Integer.toString(eventCalendar.get(Calendar.DAY_OF_MONTH));
    }
}