package sumeshgames.android.customcalender;

/**
 * Created by Sumesh on 18-01-2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // For our recurring task, we'll just display a message


        Intent i = new Intent(arg0, MyTestService.class);
      //  Toast.makeText(arg0, "I'm running", Toast.LENGTH_SHORT).show();

        arg0.startService(i);
       // createNotification((Activity)arg0);
    }
    /*public void createNotification(Activity a) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(a.getApplicationContext(), NotificationReceiverActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(a, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(a)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject").setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent)
                .addAction(R.drawable.icon, "Call", pIntent)
                .addAction(R.drawable.icon, "More", pIntent)
                .addAction(R.drawable.icon, "And more", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) a.getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }*/

}