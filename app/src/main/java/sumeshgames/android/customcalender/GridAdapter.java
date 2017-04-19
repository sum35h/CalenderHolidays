package sumeshgames.android.customcalender;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private List<EventObjects> allEvents;


    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, List<EventObjects> allEvents) {
        super(context, R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        mInflater = LayoutInflater.from(context);
        this.allEvents=allEvents;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Log.d("check","in getview");
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentDay=currentDate.get(Calendar.DAY_OF_MONTH);
        View view = convertView;
         Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        if(view == null){
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
            Log.d("check","inflatig date");
        }
        /*
        if(displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(Color.parseColor("#FF5733"));
            if(dayValue==currentDay&&displayMonth == cal.get(Calendar.MONTH)+1)
                view.setBackgroundColor(Color.rgb(111,22,25));
        }else{
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }*/

        Log.d("eee","curdate:"+currentDay+"and dayval:"+dayValue);
        //Add day to calendar
        TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));
        //Add events to the calendar
        TextView eventIndicator = (TextView)view.findViewById(R.id.event_id);

        //DatabaseHandler db=new DatabaseHandler(getContext());
        //List<EventObjects> allEvents=db.getAllEvents();
        //if(db==null)
           // Log.d("dbs","null");
       // else
           // Log.d("dbs","a1");


        //Log.d("dbs","size ====="+ allEvents.size());
  Calendar ec=Calendar.getInstance();
        Calendar eventCalendar = Calendar.getInstance();
        for(int i = 0; i < allEvents.size(); i++){
            //Log.d("dbs","inside"+allEvents.get(i).getDate().toString());
            eventCalendar.setTime(allEvents.get(i).getDate());

            if(dayValue == ec.get(Calendar.DAY_OF_MONTH) && displayMonth == ec.get(Calendar.MONTH) + 1
                    && displayYear == ec.get(Calendar.YEAR)){
                Log.e("dbs","mon cur:"+ displayMonth+ " and mon e:"+eventCalendar.get(Calendar.MONTH) + 1);
                view.setBackgroundColor(Color.rgb(33,22,111));
            }
            if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) ){
                  Log.d("aaa","event cal"+eventCalendar.get(Calendar.DAY_OF_MONTH));
                view.setBackgroundColor(Color.rgb(226, 171, 221));
                 final TextView tv=(TextView)view.findViewById(R.id.calendar_date_id);
                tv.setTextColor(Color.parseColor("#ffffff"));
            }
        }
        return view;
    }
    @Override
    public int getCount() {
        return monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }

    public void currentSellect(View view)

    {
       view.setBackgroundColor(Color.parseColor("#cccccc"));
    }
    public void prevSellect(View view)

    {
        view.setBackgroundColor(Color.parseColor("#FF5733"));
    }
}