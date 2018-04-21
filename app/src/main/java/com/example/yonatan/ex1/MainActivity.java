package com.example.yonatan.ex1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /* List View represents a list of all the messages */
    ListView details;
    ListView Messages;
    /* Send Button, each clicking is getting the text from Edit Text and
                                        inserting it to the list view */
    Button sendMessageBtn;
    /* Writing the messages that we want to insert to the list view */
    EditText newMessage;

    EditText nameOdSender;
    /* Array of messages, defined as static for landscape view */
    final static ArrayList<String> arrayOfMessages = new ArrayList<>();
    final static ArrayList<String> arrayOfdetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // define all the fields from R.id
        Messages = findViewById(R.id.contactsListView);
        details = findViewById(R.id.dateAndTimeListView);
        sendMessageBtn = findViewById(R.id.sendBtn);
        newMessage = findViewById(R.id.sendNewMessageET);
        nameOdSender = findViewById(R.id.nameOfSender);


        final ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, arrayOfMessages);

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, arrayOfdetails);

        Messages.setAdapter(adapter);
        details.setAdapter(adapter1);

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newMessage.toString().isEmpty() && (!nameOdSender.toString().isEmpty())){
                    Date d=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
                    String currentDateTimeString = sdf.format(d);
                    String content = newMessage.getText().toString();
                    String detailsString = nameOdSender.getText().toString() + "\n" + currentDateTimeString;
                    arrayOfMessages.add(content);
                    arrayOfdetails.add(detailsString);
                    details.setAdapter(adapter1);
                    Messages.setAdapter(adapter);
                    nameOdSender.setText("");
                    newMessage.setText("");
                }
            }
        });
    }
}
