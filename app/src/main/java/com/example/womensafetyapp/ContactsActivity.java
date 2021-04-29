package com.example.womensafetyapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {

    Button cancelButton, saveButton;
    EditText name1, name2, name3, name4, name5;
    public EditText phone1, phone2, phone3, phone4, phone5;
    SharedPreferences preferences;
    double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);

        name1 = findViewById(R.id.nameET1);
        name2 = findViewById(R.id.nameET2);
        name3 = findViewById(R.id.nameET3);
        name4 = findViewById(R.id.nameET4);
        name5 = findViewById(R.id.nameET5);

        phone1 = findViewById(R.id.phoneET1);
        phone2 = findViewById(R.id.phoneET2);
        phone3 = findViewById(R.id.phoneET3);
        phone4 = findViewById(R.id.phoneET4);
        phone5 = findViewById(R.id.phoneET5);

        preferences = this.getSharedPreferences("com.example.womensafetyapp", Context.MODE_PRIVATE);

        name1.setText(preferences.getString("name1", ""));
        phone1.setText(preferences.getString("phone1", ""));
        name1.setSelection(name1.getText().length());
        phone1.setSelection(phone1.getText().length());

        name2.setText(preferences.getString("name2", ""));
        phone2.setText(preferences.getString("phone2", ""));
        name2.setSelection(name2.getText().length());
        phone2.setSelection(phone2.getText().length());

        name3.setText(preferences.getString("name3", ""));
        phone3.setText(preferences.getString("phone3", ""));
        name3.setSelection(name3.getText().length());
        phone3.setSelection(phone3.getText().length());

        name4.setText(preferences.getString("name4", ""));
        phone4.setText(preferences.getString("phone4", ""));
        name4.setSelection(name4.getText().length());
        phone4.setSelection(phone4.getText().length());

        name5.setText(preferences.getString("name5", ""));
        phone5.setText(preferences.getString("phone5", ""));
        name5.setSelection(name5.getText().length());
        phone5.setSelection(phone5.getText().length());
    }

    public void cancelAction(View view) {
        finish();
    }

    public void saveAction(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name1", name1.getText().toString());
        editor.putString("phone1", phone1.getText().toString());
        editor.putString("name2", name2.getText().toString());
        editor.putString("phone2", phone2.getText().toString());
        editor.putString("name3", name3.getText().toString());
        editor.putString("phone3", phone3.getText().toString());
        editor.putString("name4", name4.getText().toString());
        editor.putString("phone4", phone4.getText().toString());
        editor.putString("name5", name5.getText().toString());
        editor.putString("phone5", phone5.getText().toString());
        editor.apply();
        Toast.makeText(getApplicationContext(), "Changes saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void sendDistress(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            sendMessageToAllContacts();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 3);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                sendMessageToAllContacts();
            }
        } else {
            Toast.makeText(getApplicationContext(), "SMS Sending Failed!", Toast.LENGTH_LONG).show();
        }
    }

    private void sendMessageToAllContacts() {
        SmsManager manager = SmsManager.getDefault();
        String defaultMessage = "Help Me! I am in danger!";
        int i = 0;

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, listener);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String locationURL = "http://maps.google.com/?q=" + latitude + "," + longitude;
        String distressMessage = preferences.getString("message", defaultMessage) + "\n\n My location:" + locationURL;

        if (phone1.getText().toString() != null && !phone1.getText().toString().equals("")) {
            manager.sendTextMessage(phone1.getText().toString(), null, distressMessage, null, null);
            i = 1;
        }
        if (phone2.getText().toString() != null && !phone2.getText().toString().equals("")) {
            manager.sendTextMessage(phone2.getText().toString(), null, distressMessage, null, null);
            i = 1;
        }
        if (phone3.getText().toString() != null && !phone3.getText().toString().equals("")) {
            manager.sendTextMessage(phone3.getText().toString(), null, distressMessage, null, null);
            i = 1;
        }
        if (phone4.getText().toString() != null && !phone4.getText().toString().equals("")) {
            manager.sendTextMessage(phone4.getText().toString(), null, distressMessage, null, null);
            i = 1;
        }
        if (phone5.getText().toString() != null && !phone5.getText().toString().equals("")) {
            manager.sendTextMessage(phone1.getText().toString(), null, distressMessage, null, null);
            i = 1;
        }

        if (i == 1)
            Toast.makeText(getApplicationContext(), "SMS Sent", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Please enter at least one contact", Toast.LENGTH_SHORT).show();
    }

    private final LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void showLocation(View view) {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, listener);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String locationString = Double.toString(longitude) + "," + Double.toString(latitude);
        Toast.makeText(getApplicationContext(), locationString, Toast.LENGTH_SHORT).show();
    }
}
