package ca.mcgill.ecse321.eventregistration;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Date;
import java.sql.Time;


import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;
import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;


public class MainActivity extends AppCompatActivity {

    private RegistrationManager rm = null;
    private String fileName;
    String error = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fileName = getFilesDir().getAbsolutePath() + "/eventregistration.xml";
        rm = PersistenceXStream.initializeModelManager(fileName);

        refreshData();
    }

    private void refreshData() {
        ((TextView) findViewById(R.id.newparticipant_name)).setText("");
        ((TextView) findViewById(R.id.newevent_name)).setText("");
        ((TextView) findViewById(R.id.newevent_date)).setText(getString(R.string.newevent_date_first));
        ((TextView) findViewById(R.id.newevent_startTime)).setText(getString(R.string.newevent_startTime_first));
        ((TextView) findViewById((R.id.newevent_endTime))).setText(getString(R.string.newevent_endTime_first));


        // Initialize the data in the participant spinner
        Spinner pspinner = (Spinner) findViewById(R.id.participantspinner);
        ArrayAdapter<CharSequence> participantAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        participantAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (Participant p: rm.getParticipants() ) {
            participantAdapter.add(p.getName());
        }
        pspinner.setAdapter(participantAdapter);

        // Initialize the data in the event spinner
        Spinner espinner = (Spinner) findViewById(R.id.eventspinner);
        ArrayAdapter<CharSequence> eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        participantAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (Event e: rm.getEvents() ) {
            eventAdapter.add(e.getName());
        }
        espinner.setAdapter(eventAdapter);

        TextView err = (TextView) findViewById(R.id.error_message); //Refresh error message
        err.setText(error);
        error = null;


    }

    public void showDatePickerDialog(View v){
        TextView tf = (TextView) v;

        Bundle args = getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    //Helper method for showDatePickerDialog
    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);
        return rtn;
    }

    //Helper method for showDatePickerDialog
    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }

    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getTimeFromLabel(tf.getText());
        args.putInt("id", v.getId());

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    //Helper method for showTimePickerDialog
    private Bundle getTimeFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split(":");
        int hour = 12;
        int minute = 0;
        if (comps.length == 2) {
            hour = Integer.parseInt(comps[0]);
            minute = Integer.parseInt(comps[1]);
        }
        rtn.putInt("hour", hour);
        rtn.putInt("minute", minute);
        return rtn;
    }

    //Helper method for showTimePickerDialog
    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }

    public void addParticipant(View v) {
        TextView tv = (TextView) findViewById(R.id.newparticipant_name);
        EventRegistrationController pc = new EventRegistrationController(rm);
        try {
            pc.createParticipant(tv.getText().toString());
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }
        refreshData();
    }

    public void registerParticipant(View v){
        Spinner pspinner = (Spinner) findViewById(R.id.participantspinner);
        Spinner espinner = (Spinner) findViewById(R.id.eventspinner);

        String participantName;
        String eventName;

        try {
            participantName = pspinner.getSelectedItem().toString();
            eventName = espinner.getSelectedItem().toString();
        }  catch (Exception e){
            participantName = null;
            eventName = null;
        }


        Event event = null;
        Participant participant = null;
        if (participantName != null && eventName != null) {
            for (Event e : rm.getEvents()) {
                if (e.getName().equals(eventName)) {
                    event = e;
                    break;
                }
            }

            for (Participant p : rm.getParticipants()) {
                if (p.getName().equals(participantName)) {
                    participant = p;
                    break;
                }
            }
        }

        EventRegistrationController pc = new EventRegistrationController(rm);


        try {
            pc.register(participant, event);
        } catch (InvalidInputException e){
            error = e.getMessage();
        }

        refreshData();
    }

    public void addEvent(View v){
        TextView eventName = (TextView) findViewById(R.id.newevent_name);
        TextView startTime = (TextView) findViewById(R.id.newevent_startTime);
        TextView endTime = (TextView) findViewById(R.id.newevent_endTime);
        TextView date = (TextView) findViewById(R.id.newevent_date);
        EventRegistrationController pc = new EventRegistrationController(rm);

        String[] d = date.getText().toString().split("-");
        String tmp= "";
        if (d.length == 3) {
            tmp = d[2]+'-'+d[1]+'-'+d[0];
        }
        Date eventDate = Date.valueOf(tmp);

        try{
            pc.createEvent(eventName.getText().toString(), eventDate, Time.valueOf(startTime.getText().toString()+":00"), Time.valueOf(endTime.getText().toString()+":00") );
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }
        refreshData();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
