package sumeshgames.android.customcalender;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Sumesh on 17-01-2017.
 */
public class MyTestService extends IntentService {
    // Must create a default constructor
    public static final String ACTION = "com.codepath.example.servicesdemo.MyTestService";
    public MyTestService() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
    }
    int temp=0;
    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
    }
/*
    @Override
    protected void onHandleIntent(Intent intent) {
        // This describes what will happen when service is triggered
        // Extract the receiver passed into the service
        ResultReceiver rec = intent.getParcelableExtra("receiver");
        // Extract additional values from the bundle
        String val = intent.getStringExtra("foo");
        // To send a message to the Activity, create a pass a Bundle
        Bundle bundle = new Bundle();
        bundle.putString("resultValue", "My Result Value. Passed in: " + val);
        // Here we call send passing a resultCode and the bundle of extras
        rec.send(Activity.RESULT_OK, bundle);
    }*/
@Override
protected void onHandleIntent(Intent i) {
    // Fetch data passed into the intent on start
    //String val = intent.getStringExtra("foo");
    // Construct an Intent tying it to the ACTION (arbitrary event namespace)
    //Intent in = new Intent(ACTION);
    // Put extras into the intent as usual
    // Prepare intent which is triggered if the
    // notification is selected
    DatabaseHandler db=new DatabaseHandler(getApplicationContext());
    String msg="";
    msg=db.getTodaysEvent();

if(msg!="")
    {
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("message",msg);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Log.d("today","msg="+msg);
        Notification noti = new Notification.Builder(getApplicationContext())
                .setContentTitle("Today's Holiday")
                .setContentText(msg).setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent)

                .build();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
       // noti.flags |= Notification.FLAG_NO_CLEAR;
        notificationManager.notify(0, noti);
      //  temp=c.get(Calendar.DAY_OF_MONTH);
    }
   // in.putExtra("resultCode", Activity.RESULT_OK);
    //in.putExtra("resultValue", "My Result Value. Passed in: " + val);
    // Fire the broadcast with intent packaged
    //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    // or sendBroadcast(in) for a normal broadcast;
}
}
