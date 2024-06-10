package com.example.safecampus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import java.util.HashMap;
import java.util.Map;

public class SpecialButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_buttons);
        final ImageButton schoolShootButton = findViewById(R.id.school_shoot_button);
        final ImageButton fireButton = findViewById(R.id.fire_button);
        final ImageButton medicalButton = findViewById(R.id.medical_button);
        final ImageButton stalkerButton = findViewById(R.id.stalker_button);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // get the last know location from your location manager.
        @SuppressLint("MissingPermission") Location location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(System.getenv("AWS_ACCESS_KEY"), System.getenv("AWS_SECRETS_KEY"));
        final AmazonSNSClient snsClient = new AmazonSNSClient(awsCreds);
        final String mapsString = String.format("https://www.google.com/maps/search/?api=1&query=%f,%f", location.getLatitude(), location.getLongitude());
        final String phoneNumber = "+15551234567";
        final Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();
//<set SMS attributes>

        //START
        schoolShootButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
//<set SMS attributes>
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendSMSMessage(snsClient, "EMERGENCY: School Shooter, my location: " + mapsString, phoneNumber, smsAttributes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                return true;
                // Code here executes on main thread after user presses button
            }
        });
        //END
        //START
        fireButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
//<set SMS attributes>
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendSMSMessage(snsClient, "EMERGENCY: Fire, my location: " + mapsString, phoneNumber, smsAttributes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                return true;
                // Code here executes on main thread after user presses button
            }
        });
        //END
        //START
        medicalButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
//<set SMS attributes>
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendSMSMessage(snsClient, "MEDICAL EMERGENCY: my location: " + mapsString, phoneNumber, smsAttributes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                return true;
                // Code here executes on main thread after user presses button
            }
        });
        //END
        //START
        stalkerButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
//<set SMS attributes>
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendSMSMessage(snsClient, "EMERGENCY: Stranger Danger, my location: " + mapsString, phoneNumber, smsAttributes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                return true;
                // Code here executes on main thread after user presses button
            }
        });
        //END
    }

    public static void sendSMSMessage(AmazonSNSClient snsClient, String message,
                                      String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        System.out.println(result);
    }
    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location loc) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    loc.getLongitude(), loc.getLatitude()
            );
            Toast.makeText(SpecialButtonActivity.this, message, Toast.LENGTH_LONG).show();
        }
        public void onProviderDisabled(String arg0) {

        }
        public void onProviderEnabled(String provider) {

        }
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }
}
