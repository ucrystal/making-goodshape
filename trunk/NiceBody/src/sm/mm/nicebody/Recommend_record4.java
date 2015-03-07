package sm.mm.nicebody;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Recommend_record4 extends Activity implements SensorEventListener {
	private static final int COUNT_DOWN_INTERVAL = 1000;
	int[][] arr = { {2, 3, 3, 3}, {4, 6, 5, 5}, {6, 9, 6, 6}, {8, 12, 8, 8}, {10, 15, 10, 10}, {12, 15, 11, 11}, {14, 18, 12, 12}, {14, 20, 14, 14}, {16, 20, 15, 15}, {20, 24, 16, 16} };
	private  int[] count;
	private int countShow;
	private int playCheck = 0;
	private TextView countTxt;
	private CountDownTimer countDownTimer;

	private Button start_btn, giveup_btn;

	private SensorManager m_sensor_manager;
	private Sensor m_sensor;
	TextView recommend_countNum;
	int printNum = 0;
	static int countResult = 0;
	static String timerResult = null;

	private TextView fixedNum1, fixedNum2, fixedNum3, fixedNum4;

	Button rec_sound_btn4;

	Sound mSound;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			new AlertDialog.Builder(this)
					.setTitle("��������")
					.setMessage("������ ���� �����Ͻðڽ��ϱ�?")
					.setPositiveButton("��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									countDownTimer.cancel();
									Intent intent = new Intent(
											Recommend_record4.this,
											Recommend_fail.class);
									startActivity(intent);
									finish();
								}
							}).setNegativeButton("�ƴϿ�", null).show();
			return false;
		default:
			return false;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend_record4);

		ActionBar actionBar = getActionBar();
		actionBar.hide();

		count=Recommend_record.count;
		
		mSound = new Sound(this, R.raw.sound);

		countTxt = (TextView) findViewById(R.id.countTxt4);

		fixedNum1 = (TextView) findViewById(R.id.fixedNum1);
		fixedNum2 = (TextView) findViewById(R.id.fixedNum2);
		fixedNum3 = (TextView) findViewById(R.id.fixedNum3);
		fixedNum4 = (TextView) findViewById(R.id.fixedNum4);

		for(int i=1; i<11; i++) {
			if (Recommend_list.choiceCh == i)
				countDownStart(i);
		}
		
		rec_sound_btn4 = (Button) findViewById(R.id.button_sound_r4);
		if(Free_record.sound_ch%2 == 1){
			rec_sound_btn4.setSelected(true);
		}else if(Free_record.sound_ch%2 == 0){
			rec_sound_btn4.setSelected(false);
		}
		rec_sound_btn4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Free_record.sound_ch += 1;
				
				if(Free_record.sound_ch%2 == 1){
					rec_sound_btn4.setSelected(true);
				}else if(Free_record.sound_ch%2 == 0){
					rec_sound_btn4.setSelected(false);
				}
				
				
			}
		});

		start_btn = (Button) findViewById(R.id.recommend_play_btn4);
		start_btn.setClickable(true);
		start_btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				countDownTimer.start();
				start_btn.setClickable(false);

			}
		});

		giveup_btn = (Button) findViewById(R.id.recommend_finish_btn4);
		giveup_btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				countDownTimer.cancel();

				Intent intent = new Intent(Recommend_record4.this,Recommend_fail.class);
				startActivity(intent);
				finish();
			}
		});

		// ��¿� �ؽ�Ʈ�並 ��´�.
		recommend_countNum = (TextView) findViewById(R.id.recommendcountNum4);

		// �ý��ۼ��񽺷κ��� SensorManager ��ü�� ��´�.
		m_sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// SensorManager �� �̿��ؼ� ���� ���� ��ü�� ��´�.
		m_sensor = m_sensor_manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	}

	// �ش� ��Ƽ��Ƽ�� ���۵Ǹ� ���� �����͸� ���� �� �ֵ��� �����ʸ� ����Ѵ�.
	protected void onStart() {
		super.onStart();

		// ���� ���� �� ���ؽ�Ʈ���� �޾ƺ� �� �ֵ��� �����ʸ� ����Ѵ�.
		m_sensor_manager.registerListener(this, m_sensor,SensorManager.SENSOR_DELAY_UI);
	}

	// �ش� ��Ƽ��Ƽ�� ���߸� ���� �����͸� �� �ҿ��� �����Ƿ� �����ʸ� �����Ѵ�.
	protected void onStop() {
		super.onStop();
		// ���� ���� �ʿ����� �ʴ� ������ �����ʸ� �������ش�.
		m_sensor_manager.unregisterListener(this);
	}

	// ��Ȯ�� ����� ȣ��Ǵ� �޼ҵ�. ������ ��� ���� ȣ����� �ʴ´�.
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	// ������ ���� �������ִ� �޼ҵ�.
	public void onSensorChanged(SensorEvent event) {
		// ��Ȯ���� ���� �������� ���
		if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
			// ��� ����� ��� accuracy �� SENSOR_STATUS_UNRELIABLE ����
			// ������ �������� ������� ���ϴ� ��찡 �ֱ⶧���� ���Ƿ� return ; �� ���´�.
			// return;
		}

		if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
			long currentTime = System.currentTimeMillis();
			long lastTime = 0;
			long gabOfTime = (currentTime - lastTime);
			float testNum = 0;

			if (gabOfTime > 100) {
				lastTime = currentTime;
				testNum = event.values[0];

				// �ð� üũ���� ���
				if (playCheck == 1) {
					// ������ �����ϸ� Ƚ������
					if (testNum == 0) {
						printNum++;

						if(Free_record.sound_ch%2 == 0){
							mSound.play();
						}
						
						if (printNum < 10)
							recommend_countNum.setText("0" + printNum);
						else
							recommend_countNum.setText("" + printNum);

						for(int i=1; i<11; i++) {
							if (Recommend_list.choiceCh == i)
								challengeSuccess(i);
						}

						// ��¸�� ����
						if (printNum < 10)
							recommend_countNum.setText("0" + printNum);
						else
							recommend_countNum.setText("" + printNum);

						// �������� �־����� Ƚ���������� ��¸�縸 ����
					} else if (testNum != 0) {

						if (printNum < 10)
							recommend_countNum.setText("0" + printNum);
						else
							recommend_countNum.setText("" + printNum);
					}

				} else if (playCheck == 2) {
					return;
				}
			}
		}

	}

	void countDownStart(int choiceNb) {
		final int index = choiceNb - 1;
		countShow = count[index];
		fixedNum1.setText("��ü " + arr[index][0]);
		fixedNum2.setText(" ���� " + arr[index][1]);
		fixedNum3.setText(" ��üR " + arr[index][2]);
		fixedNum4.setText(" ��üL " + arr[index][3]);
		countTxt.setText(String.valueOf(countShow) + "��");

		countDownTimer = new CountDownTimer(count[index] * 1000,
				COUNT_DOWN_INTERVAL) {
			public void onTick(long millisUntilFinished) {
				playCheck = 1;
				count[index]--;
				countTxt.setText(String.valueOf(count[index]) + "��");
			}

			public void onFinish() {
				playCheck = 2;
				countTxt.setText(String.valueOf("��������"));
			}
		};
	}

	void challengeSuccess(int choiceNb) {
		int index = choiceNb - 1;
		if (printNum == arr[index][3]) {
			recommend_countNum.setText("0" + printNum);
			countDownTimer.cancel();
			Intent intent = new Intent(Recommend_record4.this,Recommend_success.class);
			startActivity(intent);
			overridePendingTransition(R.anim.default_start_enter,R.anim.default_start_exit);
			finish();
		}
	}
}