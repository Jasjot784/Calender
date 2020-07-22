package com.jasjotsingh.calender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Button button;
    long time = 1595408177000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        EpochToDate(time);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent("Test","Chandigarh",time,time+20000);
            }
        });
        try {
            Long epoch = DateToEpoch();
            Log.d(TAG, "onCreate: "+epoch);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public void addEvent(String title, String location, long begin, long end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public static String EpochToDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss-dd-MM-yyyy");
        Log.d(TAG, "EpochToDate: "+format.format(new Date(time)));
        return format.format(new Date(time));

    }
    public static Long DateToEpoch() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'Time:'HH:mm:ss");
        Date ist = formatter.parse("2020-07-22Time:14:26:17");
        long millisecondsSinceEpoch0 = ist.getTime();
        String asString = formatter.format(ist);
        return millisecondsSinceEpoch0;
    }
}