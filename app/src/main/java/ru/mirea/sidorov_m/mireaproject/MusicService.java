package ru.mirea.sidorov_m.mireaproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    public static final String CHANNEL_ID = "sidorov_maxim";



    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onCreate() {
        super.onCreate();

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText("идёт воспроизведение")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Текущий трек: Slillet - Moster"))
                .setContentTitle("мой плеер");

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Сидоров М.М. Notification", importance);
        channel.setDescription("Sidorov");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.createNotificationChannel(channel);
        startForeground(1, builder.build());

        mediaPlayer = MediaPlayer.create(this, R.raw.monster);
        mediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                stopForeground(true);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        mediaPlayer.stop();
    }


}