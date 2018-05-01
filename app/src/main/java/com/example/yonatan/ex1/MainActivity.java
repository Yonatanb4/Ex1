package com.example.yonatan.ex1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static String currentUser = "Yonatan";

    static EditText input;

    static int currentIndex;

    static String detailsOfMessage = "";

    /* List View represents a list of all the messages */
    static ListView messages;

    Button changeUserBtn;

    /* Send Button, each clicking is getting the text from Edit Text and
                                        inserting it to the list view */
    Button sendMessageBtn;
    /* Writing the messages that we want to insert to the list view */
    EditText newMessage;


    /* Array of messages, defined as static for landscape view */
    static ArrayList<String> arrayOfMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // define all the fields from R.id
        messages = findViewById(R.id.contactsListView);
        sendMessageBtn = findViewById(R.id.sendBtn);
        newMessage = findViewById(R.id.sendNewMessageET);
        changeUserBtn = findViewById(R.id.changeUserBtn);

        final  ArrayAdapter<String> adapter1 = new ArrayAdapter<>
                (this, R.layout.custom_textview, arrayOfMessages);


        messages.setAdapter(adapter1);

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(newMessage.getText().toString().trim().length() == 0)){
                    String content = newMessage.getText().toString();
                    arrayOfMessages.add(content);
                    newMessage.setText("");
                    messages.setAdapter(adapter1);

                    String lastPosition = Integer.toString(messages.getCount()-1);
                    SharedPreferences.Editor editor =
                            getSharedPreferences("sp", MODE_PRIVATE).edit();
                    editor.putString(lastPosition, currentUser);
                    editor.apply();
                }
            }
        });

        messages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                updateDetailsOfMessage(Integer.toString(position));
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                MessageFragment mf = new MessageFragment();
                mf.show(manager, " My_dialog_tag");
                currentIndex = position;

                return true;
            }

            private void updateDetailsOfMessage(String position) {
                Date d=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
                String currentDateTimeString = sdf.format(d);

                SharedPreferences sp =
                        getSharedPreferences("sp", MODE_PRIVATE);
                String userOfMessage = sp.getString(position, null);
                detailsOfMessage = "Name of sender: " + userOfMessage
                        + "\n" + currentDateTimeString;
            }

        });

        changeUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                ChangeUserFragment mf = new ChangeUserFragment();
                mf.show(manager, " My_Button_tag");
//                input.setText(currentUser);
            }
        });
    }


    public static class MessageFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(MainActivity.detailsOfMessage);
            builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    arrayOfMessages.remove(currentIndex);
                    messages.setAdapter( new ArrayAdapter<>
                            (getContext(), R.layout.custom_textview, arrayOfMessages));
                }
            });

            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setTitle("Message details");
            AlertDialog dialog = builder.create();

            return dialog;

        }
    }

    public static class ChangeUserFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            input = new EditText(getContext());
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    currentUser = input.getText().toString();
                }
            });

            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setTitle("Change User");
            AlertDialog dialog = builder.create();

            return dialog;

        }
    }
}















