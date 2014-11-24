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
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Recommend_record extends Activity implements SensorEventListener {
	private static final int MILLISINFUTURE = 50 * 1000;
	private static final int COUNT_DOWN_INTERVAL = 1000;

	int[][] arr = { { 1, 2, 1 }, { 2, 4, 3 }, { 3, 4, 4 }, { 4, 7, 6 },
			{ 5, 10, 10 }, { 10, 18, 15 } };

	private int playCheck = 0;
	private int count = 50;
	private TextView countTxt;
	private CountDownTimer countDownTimer;

	private Button start_btn, giveup_btn;

	private SensorManager m_sensor_manager;
	private Sensor m_sensor;
	TextView recommend_countNum;
	int printNum = 0;
	static int countResult = 0;
	static String timerResult = null;

	private TextView fixedNum1, fixedNum2, fixedNum3;

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
									Intent intent = new Intent(Recommend_record.this,
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
		setContentView(R.layout.recommend_record);

		ActionBar actionBar = getActionBar();
		actionBar.hide();

		mSound = new Sound(this, R.raw.sound);
		
		countTxt = (TextView) findViewById(R.id.countTxt);
		countTxt.setText(String.valueOf(count) + "��");

		fixedNum1 = (TextView) findViewById(R.id.fixedNum1);
		fixedNum2 = (TextView) findViewById(R.id.fixedNum2);
		fixedNum3 = (TextView) findViewById(R.id.fixedNum3);
		if (Recommend_list.choiceCh == 1) {
			fixedNum1.setText("��ü " + arr[0][0]);
			fixedNum2.setText(" ���� " + arr[0][1]);
			fixedNum3.setText(" ��ü " + arr[0][2]);
		} else if (Recommend_list.choiceCh == 2) {
			fixedNum1.setText("��ü " + arr[1][0]);
			fixedNum2.setText(" ���� " + arr[1][1]);
			fixedNum3.setText(" ��ü " + arr[1][2]);
		} else if (Recommend_list.choiceCh == 3) {
			fixedNum1.setText("��ü " + arr[2][0]);
			fixedNum2.setText(" ���� " + arr[2][1]);
			fixedNum3.setText(" ��ü " + arr[2][2]);
		} else if (Recommend_list.choiceCh == 4) {
			fixedNum1.setText("��ü " + arr[3][0]);
			fixedNum2.setText(" ���� " + arr[3][1]);
			fixedNum3.setText(" ��ü " + arr[3][2]);
		} else if (Recommend_list.choiceCh == 5) {
			fixedNum1.setText("��ü " + arr[4][0]);
			fixedNum2.setText(" ���� " + arr[4][1]);
			fixedNum3.setText(" ��ü " + arr[4][2]);
		} else if (Recommend_list.choiceCh == 6) {
			fixedNum1.setText("��ü " + arr[5][0]);
			fixedNum2.setText(" ���� " + arr[5][1]);
			fixedNum3.setText(" ��ü " + arr[5][2]);
		}

		countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
			public void onTick(long millisUntilFinished) {
				playCheck = 1;
				countTxt.setText(String.valueOf(count) + "��");
				count--;
			}

			public void onFinish() {
				playCheck = 2;
				countTxt.setText(String.valueOf("��������"));
			}
		};

		start_btn = (Button) findViewById(R.id.recommend_play_btn);
		start_btn.setClickable(true);
		start_btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				countDownTimer.start();
				start_btn.setClickable(false);
				
			}
		});

		giveup_btn = (Button) findViewById(R.id.recommend_finish_btn);
		giveup_btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				countDownTimer.cancel();

				Intent intent = new Intent(Recommend_record.this,
						Recommend_fail.class);
				startActivity(intent);
				finish();
			}
		});

		// ��¿� �ؽ�Ʈ�並 ��´�.
		recommend_countNum = (TextView) findViewById(R.id.recommendcountNum);

		// �ý��ۼ��񽺷κ��� SensorManager ��ü�� ��´�.
		m_sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// SensorManager �� �̿��ؼ� ���� ���� ��ü�� ��´�.
		m_sensor = m_sensor_manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	}

	// �ش� ��Ƽ��Ƽ�� ���۵Ǹ� ���� �����͸� ���� �� �ֵ��� �����ʸ� ����Ѵ�.
	protected void onStart() {
		super.onStart();

		// ���� ���� �� ���ؽ�Ʈ���� �޾ƺ� �� �ֵ��� �����ʸ� ����Ѵ�.
		m_sensor_manager.registerListener(this, m_sensor,
				SensorManager.SENSOR_DELAY_UI);
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
						
						mSound.play();
						if (printNum < 10)
							recommend_countNum.setText("0" + printNum);
						else
							recommend_countNum.setText("" + printNum);
						
						if (Recommend_list.choiceCh == 1) {
							// ��ü� ���� �� ��ü� �������� �̵�
							if (printNum == arr[0][0]) {
								recommend_countNum.setText("0" + printNum);
								countDownTimer.cancel();
								Intent intent = new Intent(
										Recommend_record.this,
										Recommend_record2.class);
								startActivity(intent);
								overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
								finish();
							}
						} else if (Recommend_list.choiceCh == 2) {
							if (printNum == arr[1][0]) {
								recommend_countNum.setText("0" + printNum);
								countDownTimer.cancel();
								Intent intent = new Intent(
										Recommend_record.this,
										Recommend_record2.class);
								startActivity(intent);
								overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
								finish();
							}
						} else if (Recommend_list.choiceCh == 3) {
							if (printNum == arr[2][0]) {
								recommend_countNum.setText("0" + printNum);
								countDownTimer.cancel();
								Intent intent = new Intent(
										Recommend_record.this,
										Recommend_record2.class);
								startActivity(intent);
								overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
								finish();
							}
						} else if (Recommend_list.choiceCh == 4) {
							if (printNum == arr[3][0]) {
								recommend_countNum.setText("0" + printNum);
								countDownTimer.cancel();
								Intent intent = new Intent(
										Recommend_record.this,
										Recommend_record2.class);
								startActivity(intent);
								overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
								finish();
							}

						} else if (Recommend_list.choiceCh == 5) {
							if (printNum == arr[4][0]) {
								recommend_countNum.setText("0" + printNum);
								countDownTimer.cancel();
								Intent intent = new Intent(
										Recommend_record.this,
										Recommend_record2.class);
								startActivity(intent);
								overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
								finish();
							}
						} else if (Recommend_list.choiceCh == 6) {
							if (printNum == arr[5][0]) {
								recommend_countNum.setText("0" + printNum);
								countDownTimer.cancel();
								Intent intent = new Intent(
										Recommend_record.this,
										Recommend_record2.class);
								startActivity(intent);
								overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
								finish();
							}
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

}
