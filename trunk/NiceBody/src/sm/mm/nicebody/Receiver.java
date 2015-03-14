package sm.mm.nicebody;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;
import android.support.v4.app.NotificationCompat.Builder;

public class Receiver extends ParsePushBroadcastReceiver {
	int RECEIVER_ID = 182;

	@Override
	public void onPushOpen(Context context, Intent intent) {
		Intent i = new Intent(context, Main.class);
		i.putExtras(intent.getExtras());
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	@Override
	public void onPushDismiss(Context context, Intent intent) {
		Log.v("pushTest", "running onPushDismiss");
	}

	@Override
	public void onPushReceive(Context context, Intent intent) {
		Log.v("pushTest", "running onPushOpen");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent it = new Intent(context, Profile.class);
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		it.putExtra("RECEIVER_ID", RECEIVER_ID + "");
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, it,
				PendingIntent.FLAG_UPDATE_CURRENT);

		PendingIntent shareIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(Profile_modify.title)
				.setContentText(Profile_modify.text);
		mBuilder.setContentIntent(contentIntent);
		mBuilder.setDefaults(Notification.DEFAULT_SOUND);
		mBuilder.setAutoCancel(true);
		mBuilder.addAction(R.drawable.ic_action_search, "Watch Now",
				contentIntent).addAction(R.drawable.ic_action_share, "Share",
				shareIntent);

		mBuilder.setLargeIcon(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.ic_launcher));
		mBuilder.setStyle(new android.support.v4.app.NotificationCompat.BigPictureStyle()
				.bigPicture(
						BitmapFactory.decodeResource(context.getResources(),
								R.drawable.canimal_big))
				.setBigContentTitle(Profile_modify.title)
				.setSummaryText(Profile_modify.text));

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, mBuilder.build());
	}
}
