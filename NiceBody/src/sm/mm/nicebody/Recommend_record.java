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
					.setTitle("도전포기")
					.setMessage("도전을 정말 포기하시겠습니까?")
					.setPositiveButton("예",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Intent intent = new Intent(Recommend_record.this,
											Recommend_fail.class);
									startActivity(intent);
									finish();
								}
							}).setNegativeButton("아니오", null).show();
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
		countTxt.setText(String.valueOf(count) + "초");

		fixedNum1 = (TextView) findViewById(R.id.fixedNum1);
		fixedNum2 = (TextView) findViewById(R.id.fixedNum2);
		fixedNum3 = (TextView) findViewById(R.id.fixedNum3);
		if (Recommend_list.choiceCh == 1) {
			fixedNum1.setText("상체 " + arr[0][0]);
			fixedNum2.setText(" 복부 " + arr[0][1]);
			fixedNum3.setText(" 하체 " + arr[0][2]);
		} else if (Recommend_list.choiceCh == 2) {
			fixedNum1.setText("상체 " + arr[1][0]);
			fixedNum2.setText(" 복부 " + arr[1][1]);
			fixedNum3.setText(" 하체 " + arr[1][2]);
		} else if (Recommend_list.choiceCh == 3) {
			fixedNum1.setText("상체 " + arr[2][0]);
			fixedNum2.setText(" 복부 " + arr[2][1]);
			fixedNum3.setText(" 하체 " + arr[2][2]);
		} else if (Recommend_list.choiceCh == 4) {
			fixedNum1.setText("상체 " + arr[3][0]);
			fixedNum2.setText(" 복부 " + arr[3][1]);
			fixedNum3.setText(" 하체 " + arr[3][2]);
		} else if (Recommend_list.choiceCh == 5) {
			fixedNum1.setText("상체 " + arr[4][0]);
			fixedNum2.setText(" 복부 " + arr[4][1]);
			fixedNum3.setText(" 하체 " + arr[4][2]);
		} else if (Recommend_list.choiceCh == 6) {
			fixedNum1.setText("상체 " + arr[5][0]);
			fixedNum2.setText(" 복부 " + arr[5][1]);
			fixedNum3.setText(" 하체 " + arr[5][2]);
		}

		countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
			public void onTick(long millisUntilFinished) {
				playCheck = 1;
				countTxt.setText(String.valueOf(count) + "초");
				count--;
			}

			public void onFinish() {
				playCheck = 2;
				countTxt.setText(String.valueOf("도전실패"));
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

		// 출력용 텍스트뷰를 얻는다.
		recommend_countNum = (TextView) findViewById(R.id.recommendcountNum);

		// 시스템서비스로부터 SensorManager 객체를 얻는다.
		m_sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// SensorManager 를 이용해서 근접 센서 객체를 얻는다.
		m_sensor = m_sensor_manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	}

	// 해당 액티비티가 시작되면 근접 데이터를 얻을 수 있도록 리스너를 등록한다.
	protected void onStart() {
		super.onStart();

		// 센서 값을 이 컨텍스트에서 받아볼 수 있도록 리스너를 등록한다.
		m_sensor_manager.registerListener(this, m_sensor,
				SensorManager.SENSOR_DELAY_UI);
	}

	// 해당 액티비티가 멈추면 근접 데이터를 얻어도 소용이 없으므로 리스너를 해제한다.
	protected void onStop() {
		super.onStop();
		// 센서 값이 필요하지 않는 시점에 리스너를 해제해준다.
		m_sensor_manager.unregisterListener(this);
	}

	// 정확도 변경시 호출되는 메소드. 센서의 경우 거의 호출되지 않는다.
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	// 측정한 값을 전달해주는 메소드.
	public void onSensorChanged(SensorEvent event) {
		// 정확도가 낮은 측정값인 경우
		if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
			// 몇몇 기기의 경우 accuracy 가 SENSOR_STATUS_UNRELIABLE 값을
			// 가져서 측정값을 사용하지 못하는 경우가 있기때문에 임의로 return ; 을 막는다.
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

				// 시간 체크중일 경우
				if (playCheck == 1) {
					// 센서에 근접하면 횟수증가
					if (testNum == 0) {
						printNum++;
						
						mSound.play();
						if (printNum < 10)
							recommend_countNum.setText("0" + printNum);
						else
							recommend_countNum.setText("" + printNum);
						
						if (Recommend_list.choiceCh == 1) {
							// 상체운동 성공 시 하체운동 페이지로 이동
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

						// 출력모양 설정
						if (printNum < 10)
							recommend_countNum.setText("0" + printNum);
						else
							recommend_countNum.setText("" + printNum);

						// 센서에서 멀어지면 횟수증가없이 출력모양만 설정
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
