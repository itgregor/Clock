package com.example.iantg.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    //alarm manager
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        //initialize alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initialize our timepicker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        //initialize text update box
        update_text = (TextView) findViewById(R.id.update_text);

        // create an instance of a calendar
        final Calendar calendar = Calendar.getInstance();

        //initialize start button
        Button alarm_on = (Button) findViewById(R.id.alarm_on);
        //create and intent to alarm receiver
        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);
        //Click Listener
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setting calendar to picked time
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                //get int values of hour and minutes
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();
                //convert to string
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);
                //Format
                if (hour > 12){
                    hour_string = String.valueOf(hour-12);
                }
                if (minute < 10){
                    minute_string = "0" + String.valueOf(minute);
                }

                //method that changes update text box
                set_alarm_text("Alarm set to: " + hour_string + ":" + minute_string);

                //create a pending intent to delay intent until calendar date specified
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //Set alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pending_intent);

            }
        });

        //initialize start button
        Button timer_on = (Button) findViewById(R.id.timer_on);
        //Click Listener
        timer_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setting calendar to picked time
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                //get int values of hour and minutes
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();
                //convert to string
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);
                //Format
                if (hour > 12){
                    hour_string = String.valueOf(hour-12);
                }
                if (minute < 10){
                    minute_string = "0" + String.valueOf(minute);
                }

                //method that changes update text box
                set_alarm_text("Timer set to: " + hour_string + ":" + minute_string);

                //create a pending intent to delay intent until calendar date specified
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //Set alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pending_intent);

            }
        });

        //initialize stop button
        Button alarm_off = (Button) findViewById(R.id.alarm_off);
        alarm_off.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //method that changes update text box
                set_alarm_text("Alarm off!");

                alarm_manager.cancel(pending_intent);
            }
        });
    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }
}


