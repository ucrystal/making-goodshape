package sm.mm.nicebody;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_receiver extends BroadcastReceiver {
	private static String TAG = "Alarm Receiver";
	private final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(BOOT_ACTION)) {
			Log.d(TAG, "BOOT_ACTION : Alarm service ON");
			context.startService(new Intent("NiceBodyService"));

		}
	}
}
