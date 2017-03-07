package com.example.mathilde.androidwearnotificationexamples;

import android.app.Notification;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class VoiceReplyActivity extends AppCompatActivity {
    public static final String EXTRA_VOICE_REPLY = "VoiceReplyActivity.EXTRA_VOICE_REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_reply);
        String response = getMessageText(getIntent()).toString();
        sendResponseNotification(response);

    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(EXTRA_VOICE_REPLY);
        }
        return null;
    }

    private void sendResponseNotification(String response) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Message sent")
                .setContentText(response)
                .setSmallIcon(R.drawable.ic_sms);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(010, notificationBuilder.build());
    }

}
