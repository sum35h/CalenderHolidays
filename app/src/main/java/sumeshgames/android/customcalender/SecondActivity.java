package sumeshgames.android.customcalender;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This is the activity that is started when the user presses the notification in the status bar
 * @author paul.blundell
 */
public class SecondActivity extends Activity {
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        String msg=getIntent().getStringExtra("message");
        tv=(TextView)findViewById(R.id.tv);
        tv.setText(msg);
    }

}