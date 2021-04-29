package com.example.womensafetyapp;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    Button connectionButton;
    EditText messageBox;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int i = 0;

    public void switchActivity(View view){
        Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
        startActivity(intent);
    }

    public void saveToSharedPreferences(){
        editor = preferences.edit();
        editor.putString("message", messageBox.getText().toString());
        editor.apply();
    }

    public void resetMessageField(View view){
        messageBox.setText("");
        //saveToSharedPreferences();
        Toast.makeText(getApplicationContext(), "Message Cleared!", Toast.LENGTH_LONG).show();
    }

    public void cancelMessageField(View view){
        messageBox.setText(preferences.getString("message", ""));
        Toast.makeText(getApplicationContext(), "Changes Undone!", Toast.LENGTH_LONG).show();
    }

    public void saveMessageField(View view){
        saveToSharedPreferences();
        Toast.makeText(getApplicationContext(), "Message Saved!", Toast.LENGTH_LONG).show();
    }

    public void connectToDevice(View view){
        if(i == 0){
            i = 1;
            connectionButton.setText("Connected to Device!");
            connectionButton.setBackground(getDrawable(R.drawable.connection_button_design_connected));
        }
        else {
            i = 0;
            connectionButton.setText("Device Disconnected!");
            connectionButton.setBackground(getDrawable(R.drawable.connection_button_design_disconnected));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionButton = findViewById(R.id.connectionButton);
        messageBox = findViewById(R.id.messageET);
        preferences = this.getSharedPreferences("com.example.womensafetyapp", Context.MODE_PRIVATE);

        messageBox.setText(preferences.getString("message", ""));
        messageBox.setSelection(messageBox.getText().length());
    }
}
