package sumeshgames.android.customcalender;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.data;
import static android.R.attr.format;
import static android.R.attr.id;

/**
 * Created by Sumesh on 26-08-2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
private static final int DATABASE_VERSION=8;
    private static final String DATABASE_NAME="HolidayCalendarDB";
    private static final String TABLE_NAME="holidays";
    private static final String COLUMN_1="message";
    private static final String COLUMN_2="startdate";

    public DatabaseHandler(Context context)
    {
       super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    public void onCreate(SQLiteDatabase db)
    {
       // Log.d("db","Created");
        //    CREATE TABLE "events" ( `_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `message` TEXT NOT NULL, `reminder` TEXT NOT NULL, `end` TEXT );
        //String CREATE_ITEM_TABLE="CREATE TABLE "+TABLE_NAME+"("+
                                                             //  COLUMN_ID+" INTEGER PRIMARY KEY,"+
                                                             //    COLUMN_NAME+" TEXT)";
        String CREATE_EVENTS_TABLE=   " CREATE TABLE holidays ( message TEXT NOT NULL, startdate TEXT NOT NULL );";
        db.execSQL(CREATE_EVENTS_TABLE);
        FillTable(db);
    }

     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

         db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

         onCreate(db);
     }



    public List<String> getAllLabels()
    {
        List<String> list=new ArrayList<String>();

        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                list.add(cursor.getString(1));

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    ////////////////////////////////////////////////////////////////
    public List<EventObjects> getAllEvents()
    {
        Date dateToday = new Date();
        List<EventObjects> events=new ArrayList<EventObjects>();

        String selectQuery="select * from holidays";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        Log.d("dbs"," get events "+cursor.moveToFirst());
        /*if(cursor.moveToFirst())
        {
            do{
                list.add(cursor.getString(1));

            }while(cursor.moveToNext());
        }*/
        if(cursor.moveToFirst()) {
            do {

                //Log.d("dbms","db cur");
              //  int id = cursor.getInt(0);
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                String startDate = cursor.getString(cursor.getColumnIndexOrThrow("startdate"));
                //convert start date to date object
                Date reminderDate = convertStringToDate(startDate);
               // if (reminderDate.after(dateToday) || reminderDate.equals(dateToday)) {
                    events.add(new EventObjects(id, message, reminderDate));
               // }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }
    /////////////////////////////////////////////////////////////////////////
    /*public List<EventObjects> getAllFutureEvents(){
       Date dateToday = new Date();
        List<EventObjects> events = new ArrayList<>();
        String query = "select * from reminder";
        //Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                String startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
                //convert start date to date object
                Date reminderDate = convertStringToDate(startDate);
                if(reminderDate.after(dateToday) || reminderDate.equals(dateToday)){
                    events.add(new EventObjects(id, message, reminderDate));
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    ////////////////////////////////////////////
    */
    public List<EventObjects> getMonthEvents(int m)
    { String mon;
        m++;
                mon=Integer.toString(m);
        if(m<10)
           mon="0"+mon;

       String q="_____-"+mon+"-__";
        Log.e("dbm","month of ge month is "+q);
        Date dateToday = new Date();
        List<EventObjects> events=new ArrayList<EventObjects>();

        String selectQuery="select * from holidays where startdate like '____-"+mon+"-__'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        Log.d("dbs"," get events "+cursor.moveToFirst());
        /*if(cursor.moveToFirst())
        {
            do{
                list.add(cursor.getString(1));

            }while(cursor.moveToNext());
        }*/
        if(cursor.moveToFirst()) {
            do {

                //Log.d("dbms","db cur");
                //  int id = cursor.getInt(0);
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                String startDate = cursor.getString(cursor.getColumnIndexOrThrow("startdate"));
                //convert start date to date object
                Date reminderDate = convertStringToDate(startDate);
                // if (reminderDate.after(dateToday) || reminderDate.equals(dateToday)) {
                events.add(new EventObjects(id, message, reminderDate));
                // }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }
    //////////////////////////////////////

    private Date convertStringToDate(String dateInString){
      //  DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void ins()
    {SQLiteDatabase db=this.getReadableDatabase();

        db.execSQL("Insert into events values ('bbb','11-1-2017')");
    }
    public void insertEvent(String message,String date)
    {
        Log.d("ppp","insert "+message+" date:"+date);
        SQLiteDatabase db=this.getWritableDatabase();
        //ContentValues values = new ContentValues();
       // values.put(COLUMN_1,message);
        //values.put(COLUMN_2,date);
        //db.insert(TABLE_NAME,null,values);
        db.execSQL("Insert into events values ('"+message+"','"+date+"')");
        db.close();
    }
    public void FillinTable()
    { SQLiteDatabase db=this.getWritableDatabase();
        Log.d("DBaaa","in FillTable");
        db.execSQL("delete from holidays;");
        db.execSQL(" insert into holidays values('Pongal ' ,'2017-01-14');");
        db.execSQL(" insert into holidays values('Makar Sankranti ' ,'2017-01-14');");
        db.execSQL(" insert into holidays values('Republic Day ' ,'2017-01-26');");
        db.execSQL(" insert into holidays values('Vasant Panchami ' ,'2017-02-01');");
        db.execSQL(" insert into holidays values('Guru Ravidas Jayanti ' ,'2017-02-10');");
        db.execSQL(" insert into holidays values('Shivaji Jayanti ' ,'2017-02-19');");
        db.execSQL(" insert into holidays values('Maharishi Dayanand Saraswati Jayanti ','2017-02-21');");

       db.execSQL(" insert into holidays values('Maha Shivaratri ' ,'2017-02-24');");
        db.execSQL(" insert into holidays values('Holika Dahana ' ,'2017-03-12');");
        db.execSQL(" insert into holidays values('Dolyatra ' ,'2017-03-13');");
        db.execSQL(" insert into holidays values('Chaitra Sukhladi ' ,'2017-03-28');");
        db.execSQL(" insert into holidays values('Rama Navami ' ,'2017-04-04');");
        db.execSQL(" insert into holidays values('Mahavir Jayanti ' ,'2017-04-09');");
        db.execSQL(" insert into holidays values('Hazarat Ali Birthday ' ,'2017-04-11');");
        db.execSQL(" insert into holidays values('Vaisakhi ' ,'2017-04-13');");
        db.execSQL(" insert into holidays values('Good Friday ' ,'2017-04-14');");
        db.execSQL(" insert into holidays values('Ambedkar Jayanti ' ,'2017-04-14');");
        db.execSQL(" insert into holidays values('Vaisakhadi ' ,'2017-04-15');");
        db.execSQL(" insert into holidays values('Easter Day ' ,'2017-04-16');");
        db.execSQL(" insert into holidays values('Buddha Purnima ' ,'2017-05-10');");

        db.execSQL(" insert into holidays values('Jamat Ul Vida  ' ,'2017-06-23');");
        db.execSQL(" insert into holidays values('Rath Yatra ' ,'2017-06-25');");

        db.execSQL(" insert into holidays values('Ramzan Id Eid ul Fitar' ,'2017-06-26');");

        db.execSQL(" insert into holidays values(' Rakhi' ,'2017-08-07');");
        db.execSQL(" insert into holidays values('Independence Day ' ,'2017-08-15');");
        db.execSQL(" insert into holidays values('Janmashtami ' ,'2017-08-15');");
        db.execSQL(" insert into holidays values('Ganesh Chaturthi ' ,'2017-08-25');");

        db.execSQL(" insert into holidays values('Id Eid ul Adha' ,'2017-09-02');");
        db.execSQL(" insert into holidays values('Onam ' ,'2017-09-04');");
        db.execSQL(" insert into holidays values('Maha Saptami ' ,'2017-09-27');");
      db.execSQL(" insert into holidays values('Maha Navami ' ,'2017-09-29');");
       db.execSQL(" insert into holidays values('Dussehra ' ,'2017-09-30');");
      db.execSQL(" insert into holidays values('Muharram ' ,'2017-10-01');");
        db.execSQL(" insert into holidays values('Mahatma Gandhi Jayanti ' ,'2017-10-02');");
        db.execSQL(" insert into holidays values('Maharishi Valmiki Jayanti ' ,'2017-10-05');");


        db.execSQL(" insert into holidays values(' Karva Chauth' ,'2017-10-08');");
        db.execSQL(" insert into holidays values('Naraka Chaturdasi ' ,'2017-10-18');");
        db.execSQL(" insert into holidays values('Diwali ' ,'2017-10-19');");
        db.execSQL(" insert into holidays values('Govardhan Puja ' ,'2017-10-20');");
        db.execSQL(" insert into holidays values('Bhai Duj ' ,'2017-10-21');");

        db.execSQL(" insert into holidays values(' Chhat Puja' ,'2017-10-26');");
        db.execSQL(" insert into holidays values('Guru Nanak Jayanti ' ,'2017-11-04');");
        db.execSQL(" insert into holidays values('Guru Tegh Bahadur Martyrdom Day ' ,'2017-11-24');");
        db.execSQL(" insert into holidays values('Milad un Nabi Id e Milad' ,'2017-12-02');");
        db.execSQL(" insert into holidays values('Christmas Eve ' ,'2017-12-24');");
        db.execSQL(" insert into holidays values('Christmas ' ,'2017-12-25');");


    }
    public void FillTable(SQLiteDatabase db)
    {
        Log.d("DBaaa","in FillTable");
        String data="\n" +
                " insert into holidays values('Pongal ' ,'2017-01-14');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Makar Sankranti ' ,'2017-01-14');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Republic Day ' ,'2017-01-26');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Vasant Panchami ' ,'2017-02-01');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Guru Ravidas Jayanti ' ,'2017-02-10');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Shivaji Jayanti ' ,'2017-02-19');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Maharishi Dayanand Saraswati Jayanti ' ,'2017-02-21');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Maha Shivaratri/Shivaratri ' ,'2017-02-24');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Holika Dahana ' ,'2017-03-12');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Dolyatra ' ,'2017-03-13');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Chaitra Sukhladi ' ,'2017-03-28');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Rama Navami ' ,'2017-04-04');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Mahavir Jayanti ' ,'2017-04-09');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Hazarat Ali's Birthday ' ,'2017-04-11');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Vaisakhi ' ,'2017-04-13');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Good Friday ' ,'2017-04-14');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Ambedkar Jayanti ' ,'2017-04-14');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Mesadi/Vaisakhadi ' ,'2017-04-15');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Easter Day ' ,'2017-04-16');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Buddha Purnima/Vesak ' ,'2017-05-10');\n" +
                "\n" +
                "\n" +

                " insert into holidays values('Jamat Ul Vida ' ,'2017-06-23');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Rath Yatra ' ,'2017-06-25');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Ramzan Id/Eid ul Fitar\n" +

                "\n" +
                "\n" +

                " insert into holidays values(' Raksha Bandhan/Rakhi' ,'2017-08-07');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Independence Day ' ,'2017-08-15');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Janmashtami ' ,'2017-08-15');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Ganesh Chaturthi/Vinayaka Chaturthi ' ,'2017-08-25');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Bakr Id/Eid ul\n" +
                " insert into holidays values('Adha ' ,'2017-09-02');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Onam ' ,'2017-09-04');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Maha Saptami ' ,'2017-09-27');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Maha Navami ' ,'2017-09-29');\n" +
                "\n" +
                "\n" +

                " insert into holidays values('Dussehra /Maha Navami' ,'2017-09-30');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Muharram/Ashura ' ,'2017-10-01');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Mahatma Gandhi Jayanti ' ,'2017-10-02');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Maharishi Valmiki Jayanti ' ,'2017-10-05');\n" +
                "\n" +
                "\n" +

                " insert into holidays values(' Karaka Chaturthi' ,'2017-10-08');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Naraka Chaturdasi ' ,'2017-10-18');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Diwali/Deepavali ' ,'2017-10-19');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Govardhan Puja ' ,'2017-10-20');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Bhai Duj ' ,'2017-10-21');\n" +
                "\n" +
                "\n" +


                " insert into holidays values('Chhat Puja/Pratihar Sashthi/Surya Sashthi ' ,'2017-10-26');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Guru Nanak Jayanti ' ,'2017-11-04');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Guru Tegh Bahadur's Martyrdom Day ' ,'2017-11-24');\n" +
                "\n" +
                "\n" +

                " insert into holidays values(' Milad Nabi e Milad ' ,'2017-12-02');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Christmas Eve ' ,'2017-12-24');\n" +
                "\n" +
                "\n" +
                " insert into holidays values('Christmas ' ,'2017-12-25');\n";
        db.execSQL(data);

    }
    public String getHoliday(String date)
    {

        String selectQuery="select * from holidays where startdate like '"+date+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        Log.d("dbs"," get events "+cursor.moveToFirst());
        String message="";
        if(cursor.moveToFirst()) {
            do {


                message = message + " " + cursor.getString(cursor.getColumnIndexOrThrow("message"));


            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return message;
    }

    ///////////
    public String getTodaysEvent()
    {
        Calendar today=Calendar.getInstance();
        String tDate="";String m="";
        int month=today.get(Calendar.MONTH)+1;
        if(month<10)
            m="0"+Integer.toString(month);
        else
           m=Integer.toString(month);

        Date dateToday = new Date();
        String str="";
         tDate=Integer.toString(today.get(Calendar.YEAR))+"-"+m+"-"+Integer.toString(today.get(Calendar.DAY_OF_MONTH));
        Log.d("today","Date: "+tDate);

        String selectQuery="select * from holidays where startdate like '"+tDate+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        //Log.d("dbs"," get events "+cursor.moveToFirst());

        if(cursor.moveToFirst()) {
            do {

                //Log.d("dbms","db cur");
                //  int id = cursor.getInt(0);
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                Log.d("today","db="+message);
                //String startDate = cursor.getString(cursor.getColumnIndexOrThrow("startdate"));
                //convert start date to date object
                //Date reminderDate = convertStringToDate(startDate);
                // if (reminderDate.after(dateToday) || reminderDate.equals(dateToday)) {
                str=message;
                // }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return str;
    }
}
