package sumeshgames.android.customcalender;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        CalenderCustomView mView = (CalenderCustomView)findViewById(R.id.custom_calendar);
        DatabaseHandler db=new DatabaseHandler(getApplicationContext());
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(!prefs.getBoolean("firstTime", false)) {

           // List<EventObjects> holidays=db.getAllEvents();
            //Calendar c=Calendar.getInstance();
       // c.set(2017, 01, 14);
       // c.set(Calendar.HOUR_OF_DAY, 0);
       // c.set(Calendar.MINUTE, 0);
       // c.set(Calendar.SECOND, 0);
        //Calendar today=Calendar.getInstance();
          // for(int i = 0; i < holidays.size(); i++){
                //Log.d("dbs","inside"+allEvents.get(i).getDate().toString());
               // c.setTime(holidays.get(i).getDate());
              // if(c.DAY_OF_MONTH==today.DAY_OF_MONTH&&c.MONTH==today.MONTH&&c.YEAR==today.YEAR) {
             //scheduleClient = new ScheduleClient(getApplicationContext());
                //   scheduleClient.doBindService();
                 //  scheduleClient.setAlarmForNotification(c);
                   //break;
               //}
              //}
            db.FillinTable();

            SharedPreferences.Editor editor = prefs.edit();
           editor.putBoolean("firstTime", true);
            editor.commit();
      }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start_noti:

                startAlarm();
                return true;


            case R.id.action_stop_noti:
               cancelAlarm();
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public void startAlarm() {
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 10000;
        long lapse=AlarmManager.INTERVAL_HALF_DAY;
       Calendar dt=Calendar.getInstance();
        dt.setTimeInMillis(System.currentTimeMillis());
        dt.set(Calendar.HOUR_OF_DAY,1);
        dt.set(Calendar.MINUTE,00);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), lapse, pendingIntent);
       // manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(),
             //   AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
    }
    public void cancelAlarm() {
        /*if (manager != null) {
            manager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }*/

        // Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        //pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Notifications Disabled", Toast.LENGTH_SHORT).show();
    }
}
