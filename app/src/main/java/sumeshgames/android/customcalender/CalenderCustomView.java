package sumeshgames.android.customcalender;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

//import sumeshgames.android.customcalender.database.DatabaseQuery;

public class CalenderCustomView extends LinearLayout{
    //private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private GridView calendarGridView;
    private Button addEventButton;
    private static final int MAX_CALENDAR_COLUMN = 35;//6 rows X 7 cols
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private GridAdapter mAdapter;
    private TextView dispText;
    private ListView list;
    String  sDate;
   // private DatabaseQuery mQuery;
    public  CalenderCustomView(Context context) {
        super(context);
    }
    public  CalenderCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUILayout();
       setUpCalendarAdapter();
        setPreviousButtonClickEvent();
       setNextButtonClickEvent();
        setGridCellClickEvents();
        Log.d(TAG, "I need to call this method");
        setEventButton();
    }

    public CalenderCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initializeUILayout(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calender_layout, this);
        previousButton = (ImageView)view.findViewById(R.id.previous_month);
        nextButton = (ImageView)view.findViewById(R.id.next_month);
        currentDate = (TextView)view.findViewById(R.id.display_current_date);
        //addEventButton = (Button)view.findViewById(R.id.add_calendar_event);
        calendarGridView = (GridView)view.findViewById(R.id.calendar_grid);
       // dispText=(TextView) findViewById(R.id.disp_date);
      //  addEventButton.setVisibility(View.INVISIBLE);
        list=(ListView)findViewById(R.id.list);

    }
    private void setPreviousButtonClickEvent(){
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setNextButtonClickEvent(){
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setEventButton()
    {/*
        addEventButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Event:");

// Set up the input
                final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String msg = input.getText().toString();
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        Log.d("inserting","msg: "+msg+" sDate: "+sDate);
                        db.insertEvent(msg,sDate);

                       // db.ins();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                //DatabaseHandler db = new DatabaseHandler(getContext());
               // db.ins();

            }
        });*/


    }
    View cview=null;
    View pview=null;
    String index[] =new String [31];
    private void setGridCellClickEvents(){
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // addEventButton.setVisibility(View.VISIBLE);
                if(index[position+1]!=null) {
                    int p=position+1;
                    //Toast.makeText(context, (p) + " : " + index[p], Toast.LENGTH_SHORT).show();



                   // Log.d("var"," day="+p+"---"+map[p]);
                   // Log.d("var","val="+myListAdapter.getCount());
                 // String s=index[p].substring(0,index[p].indexOf(':'));

                    for(int i=0;i<myListAdapter.getCount();i++) {
                        String str=myListAdapter.getItem(i);
                        str=str.substring(0,str.indexOf(' '));
                        int d=Integer.parseInt(str);
                        Log.d("var"," string ="+str);

                        if(p==d){
                            //

                           getViewByPositionSel(i,list).setBackgroundColor(Color.parseColor("#e2abdd"));
                           // list.getChildAt(i).setBackgroundColor(Color.parseColor("#e2abdd"));
                          //list.setSelection(i);

                    //"#3F51B5"

                            }else {
                            getViewByPosition(i,list).setBackgroundColor(Color.parseColor("#3F51B5"));
                           // list.getChildAt(i).setBackgroundColor(Color.parseColor("#3F51B5"));
                        }
                            }
                    
               // pview=cview;
               // cview=view;
               // mAdapter.currentSellect(cview);
               // if(pview!=null)
               // mAdapter.prevSellect(pview);
               //  sDate=(position+1)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
               // dispText.setText(sDate);
            }
        }
    });
    }

    //int map []=null;int n=0;

    private void setUpCalendarAdapter(){
        for(int i=0;i<31;i++)
        index[i]=null;


        List<Date> dayValueInCells = new ArrayList<Date>();
        //mQuery = new DatabaseQuery(context);
       // List<EventObjects> mEvents = mQuery.getAllFutureEvents();
        Calendar mCal = (Calendar)cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;// day of week {sun,mon..} starts from 0th index so...
       // mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        /*
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }*/

        do
        {
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        } while(mCal.get(Calendar.DAY_OF_MONTH)!=1);
        DatabaseHandler db=new DatabaseHandler(getContext());
        List<EventObjects> holi=db.getMonthEvents(cal.get(Calendar.MONTH));

        int nmap[]=new int[31] ;

        String str[]=new String [holi.size()];
        //Log.d("var",""+mCal.get(Calendar.DAY_OF_MONTH));
        for(int i=0,n=0;i<holi.size();i++)
        {
            EventObjects obj=holi.get(i);
            str[i]=obj.getDay()+" : "+obj.getMessage();
            index[Integer.parseInt(obj.getDay())]=obj.getMessage();
          // nmap[Integer.parseInt(obj.getDay())]=n++;
            //Log.d("var"," day="+Integer.parseInt(obj.getDay())+"---"+(n-1));
        }
       list.setAdapter(null);
       // map=nmap;
        Log.d("dbs","size ====="+ holi.size());

        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
      //  mAdapter = new GridAdapter(getContext(), dayValueInCells, cal, null);
        mAdapter = new GridAdapter(getContext(), dayValueInCells, cal, holi);
        calendarGridView.setAdapter(mAdapter);
        myListAdapter=null;
         myListAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_view_layout,R.id.list_content, str);
        //android.widget.ListView list=(ListView)findViewById(R.id.list);
        list.setAdapter(myListAdapter);
    }
    ArrayAdapter<String> myListAdapter;
    public View getViewByPositionSel(int position, ListView listView) {
       // listView.setSelection(position);
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        listView.setSelection(position);

        if (position < firstListItemPosition || position > lastListItemPosition ) {

           // listView.setSelection(position);
            return listView.getAdapter().getView(position, null, listView);
        } else {
            final int childIndex = position - firstListItemPosition;
           // listView.setSelection(childIndex);
            return listView.getChildAt(childIndex);
        }
    }
    public View getViewByPosition(int position, ListView listView) {
        // listView.setSelection(position);
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition ) {
            //listView.setSelection(position);

            return listView.getAdapter().getView(position, null, listView);
        } else {
            final int childIndex = position - firstListItemPosition;
           // listView.setSelection(childIndex);
            return listView.getChildAt(childIndex);
        }
    }
}
