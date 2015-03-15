package sm.mm.nicebody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import sm.mm.nicebody.Schedule_calendar_day;

public class Alarm_service extends Service {
	private static String TAG = "Alarm Service";
	private boolean mRunning;
	private static final int DELAY_TIME = 24 * 60 * 60 * 1000;
	//private static final int DELAY_TIME =  60 * 1000;
	private static Schedule_calendar_day day;
	private int output;

	private static List<FreeData> alarm_gym = new LinkedList<FreeData>();
	private static List<RecommendData> alarm_gym_r = new LinkedList<RecommendData>();

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate()");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy()");
		super.onDestroy();
		mRunning = true;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(TAG, "onBind()");
		return null;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					Alarm_service.this);
			builder.setSmallIcon(R.drawable.ic_launcher)
					.setContentTitle("���Ű� ����Gym")
					.setContentText("��� �������� ���� �ʾҾ��!").setAutoCancel(true);

			Intent intent = new Intent(getApplicationContext(), Main.class);
			PendingIntent pIntent = PendingIntent.getActivity(
					getApplicationContext(), 0, intent,
					Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			builder.setContentIntent(pIntent);
			manager.notify(1, builder.build());
		};
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("service", "onStartCommand ����");
		// handler ���� Thread �̿�
		new Thread(new Runnable() {

			@Override
			public void run() {
				mRunning = true;

				try {
					while (mRunning) {
						Calendar todayCalendar = Calendar.getInstance();
						Context context = getApplicationContext();
						Database alarm_db = new Database(context);
						alarm_db.getWritableDatabase();

						String alarmyear = String.valueOf(todayCalendar
								.get(Calendar.YEAR));
						String alarmmonth = String.valueOf(todayCalendar
								.get(Calendar.MONTH) + 1);
						String alarmdate = String.valueOf(todayCalendar
								.get(Calendar.DAY_OF_MONTH));
						String alarm_r = "";

						if (Integer.parseInt(alarmdate) < 10
								&& Integer.parseInt(alarmmonth) < 10) {
							alarm_r = alarmyear + "0" + alarmmonth + "0"
									+ alarmdate;
						} else if (Integer.parseInt(alarmdate) >= 10
								&& Integer.parseInt(alarmmonth) < 10) {
							alarm_r = alarmyear + "0" + alarmmonth + alarmdate;
						} else if (Integer.parseInt(alarmdate) < 10
								&& Integer.parseInt(alarmmonth) >= 10) {
							alarm_r = alarmyear + alarmmonth + "0" + alarmdate;
						} else if (Integer.parseInt(alarmdate) >= 10
								&& Integer.parseInt(alarmmonth) >= 10) {
							alarm_r = alarmyear + alarmmonth + alarmdate;
						}
						Log.d("alarm_r", alarm_r);

						alarm_gym = alarm_db.getFreeDatasByDate("'" + alarm_r
								+ "'");

						alarm_gym_r = alarm_db.getRecommendDatasByDate("'"
								+ alarm_r + "'");

						Log.d("ALARM1", Integer.toString(alarm_gym.size()));

						if ((alarm_gym.size() > 0 || alarm_gym_r.size() > 1)) {
							Log.d("ALARM2", Integer.toString(alarm_gym.size()));
							Log.d("ALARM2_r",
									Integer.toString(alarm_gym_r.size()));
							alarm_db.updateRecordData(0, alarm_r);
						} else {
							Log.d("ALARM3", Integer.toString(alarm_gym.size()));

							output = alarm_db.getRecordData().getCheckInt();
							String output_t = alarm_db.getRecordData()
									.getRecordDate();

							Log.d("output1", Integer.toString(output));

							if (output == 2) {

								mHandler.sendEmptyMessage(0);

								output = 0;
								alarm_db.updateRecordData(output, alarm_r);
							} else {

								if (output_t.equals(alarm_r) != true
										&& output_t.equals('0') != true) {
									output += 1;
									alarm_db.updateRecordData(output, alarm_r);
								}
							}

							Log.d("output2", Integer.toString(output));

						}

						alarm_db.close();

						SystemClock.sleep(DELAY_TIME);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();

		return START_REDELIVER_INTENT;
	}

}