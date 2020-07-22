package com.jasjotsingh.calender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Button button,buttonPick;
    long time = 1595408177000L;
    EditText etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        buttonPick = findViewById(R.id.buttonDate);
        etEmail = findViewById(R.id.etEmail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etEmail.getText().toString())){
                    addEvent("Appointment with doctor","Chandigarh",time,time+20000);
                }else {
                    Toast.makeText(MainActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etEmail.getText().toString())){
                    DialogFragment fragment = new DatePickerFragment();
                    fragment.show(getSupportFragmentManager(),"Pick date");
                }else {
                    Toast.makeText(MainActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }

            }
        });
//        try {
//            Long epoch = DateToEpoch();
//            Log.d(TAG, "onCreate: "+epoch);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


    }
    public void addEvent(String title, String location, long begin, long end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
                .putExtra(Intent.EXTRA_EMAIL,etEmail.getText().toString())
                .putExtra(Intent.EXTRA_TEXT,"Please ignore the link to google meet")
                .putExtra(Intent.EXTRA_SUBJECT,"Ignore Google Meet link");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public static String EpochToDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss-dd-MM-yyyy");
        Log.d(TAG, "EpochToDate: "+format.format(new Date(time)));
        return format.format(new Date(time));

    }
    public static Long DateToEpoch(String s) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'Time:'HH:mm:ss");
        Date ist = formatter.parse("2020-07-22Time:14:26:17");//2020-10-24Time:00:00:00
        ist = formatter.parse(s);
        long millisecondsSinceEpoch0 = ist.getTime();
        String asString = formatter.format(ist);
        return millisecondsSinceEpoch0;
    }
    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string +
                "/" + month_string + "/" + year_string);
        Toast.makeText(this, "You selected " + dateMessage,
                Toast.LENGTH_SHORT).show();
        try {
           time = DateToEpoch(year_string +"-"+(month+1)+"-"+day+"Time:00:00:00");
            Log.d(TAG, "processDatePickerResult: "+time);
        } catch (ParseException e) {
            Log.d(TAG, "processDatePickerResult: "+e.toString()+year +"-"+month+"-"+day+"Time:00:00:00");
            e.printStackTrace();
        }
    }
}