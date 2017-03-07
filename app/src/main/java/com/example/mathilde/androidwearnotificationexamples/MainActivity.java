package com.example.mathilde.androidwearnotificationexamples;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.speech.tts.Voice;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.notification_button)
    public void onSendNotificationClicked(){
        sendNotification();
    }

    @OnClick(R.id.notification_action_button)
    public void onSendNotificationActionClicked(){
        sendNotificationAction();
    }

    @OnClick(R.id.notification_inline_action_button)
    public void onSendNotificationInlineActionClicked(){
        sendNotificationInlineAction();
    }

    @OnClick(R.id.notification_pre_defined_button)
    public void onSendNotificationPreDefinedClicked(){
        sendNotificationPreDefinedResponses();
    }

    @OnClick(R.id.notification_big_button)
    public void onSendNotificationBigClicked(){
        sendBigNotification();
    }

    private void sendNotification(){
        int notificationId = 001;
        String title = "Calendar";
        String text = "You have an appointment in 1 hour";

        Intent viewIntent = ViewEventActivity.createIntent(this, title, text);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this,0,viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_sms)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(viewPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId,notificationBuilder.build());

    }

    private void sendNotificationAction(){
        int notificationId = 002;
        String title = "Calendar";
        String text = "Go to Elliot's house";

        Intent viewIntent = ViewEventActivity.createIntent(this, title, text);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this,0,viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode("40.663931,-73.938275"));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 0, mapIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_sms)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setContentIntent(viewPendingIntent)
                        .addAction(R.drawable.ic_map, "See on map", mapPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId,notificationBuilder.build());

    }

    private void sendNotificationInlineAction(){
        int notificationId = 003;
        String title = "Calendar";
        String text = "Go to Elliot's house";

        Intent viewIntent = ViewEventActivity.createIntent(this, title, text);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this,0,viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode("40.663931,-73.938275"));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 1, mapIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action.WearableExtender actionExtender =
                new NotificationCompat.Action.WearableExtender()
                .setHintLaunchesActivity(true)
                .setHintDisplayActionInline(true);

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                .setHintHideIcon(true);

        NotificationCompat.Action.Builder actionBuilder = new NotificationCompat.Action.Builder(
                R.drawable.ic_map, "See on map", mapPendingIntent);

        wearableExtender.addAction(actionBuilder.extend(actionExtender).build());

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_sms)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setContentIntent(viewPendingIntent)
                        .extend(wearableExtender);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId,notificationBuilder.build());

    }

    private void sendNotificationPreDefinedResponses(){
        int notificationId = 004;
        String title = "Tyrell";
        String text = "Can you see me?";

        String replyLabel = "Reply";
        String[] replyChoices = getResources().getStringArray(R.array.reply_choices);

        RemoteInput remoteInput = new RemoteInput.Builder(VoiceReplyActivity.EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .setChoices(replyChoices)
                .build();

        Intent voiceReplyIntent = new Intent(this, VoiceReplyActivity.class);
        PendingIntent voiceReplyPendingIntent = PendingIntent.getActivity(this,0,voiceReplyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(
                 R.drawable.ic_reply, "Reply", voiceReplyPendingIntent)
                  .addRemoteInput(remoteInput)
                  .build();

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_sms)
                        .setContentTitle(title)
                        .setContentText(text)
                        .extend(new NotificationCompat.WearableExtender().addAction(action));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId,notificationBuilder.build());

    }
/*
    private void sendNotificationSmartReply(){
        int notificationId = 005;
        String title = "Joanna";
        String text = "Do you really want to say no to me?";

        String replyLabel = "Reply";

        RemoteInput remoteInput = new RemoteInput.Builder(VoiceReplyActivity.EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .build();

        Intent replyIntent = new Intent(this, VoiceReplyActivity.class);
        PendingIntent replyPendingIntent = PendingIntent.getActivity(this,0,replyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(
                        R.drawable.ic_reply, "Reply", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .setAllowGeneratedReplies(true)
                        .build();


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_sms)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setStyle(new NotificationCompat.MessagingStyle("reply"))
                        //.addMessage(text, 1, title)
                        .extend(new NotificationCompat.WearableExtender().addAction(action));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId,notificationBuilder.build());

    }
*/
    private void sendBigNotification(){
        int notificationId = 006;
        String title = "Tyrell";
        String text = "I don't know what's gotten into you. We're supposed to be gods together, yet you want to destroy our destiny? You're not making sense.";

        Intent viewIntent = ViewEventActivity.createIntent(this, title, text);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this,0,viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(text);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_sms)
                        .setContentTitle(title)
                        .setContentIntent(viewPendingIntent)
                        .setStyle(bigStyle);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId,notificationBuilder.build());

    }
}
