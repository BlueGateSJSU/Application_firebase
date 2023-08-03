package com.bluegate.autodoor;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.bluegate.autodoor.activity.MainActivity;

public class Notification {
    private Context context;

    public Notification(Context context) {
        this.context = context;
    }

    public static final String CHANNEL_ID = "my_channel_id";
    public static final String CHANNEL_NAME = "My Channel";

    public void updateNotification(boolean isNotificationEnabled) {
        if (isNotificationEnabled) {
            // 배너 알림을 표시
            clearNotification();
            showBannerNotification(context, "알림", "문이 열렸습니다.");
        } else {
            clearNotification();
            showBannerNotification(context, "알림", "문이 닫혔습니다.");
        }
    }

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static final int NOTIFICATION_ID = 1;

    public static void showBannerNotification(Context context, String title, String message) {
        createNotificationChannel(context);

        Intent intent = new Intent(context, MainActivity.class); // 배너를 클릭했을 때 실행될 액티비티 지정
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void clearNotification() {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.cancel(NOTIFICATION_ID);
    }

}
