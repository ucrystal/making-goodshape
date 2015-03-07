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
					.setTitle("도전포기")
					.setMessage("도전을 정말 포기하시겠습니까?")
					.setPositiveButton("예",
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
							}).setNegativeButton("아니오", null).show();
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

		// 출력용 텍스트뷰를 얻는다.
		recommend_countNum = (TextView) findViewById(R.id.recommendcountNum4);

		// 시스템서비스로부터 SensorManager 객체를 얻는다.
		m_sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// SensorManager 를 이용해서 근접 센서 객체를 얻는다.
		m_sensor = m_sensor_manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	}

	// 해당 액티비티가 시작되면 근접 데이터를 얻을 수 있도록 리스너를 등록한다.
	protected void onStart() {
		super.onStart();

		// 센서 값을 이 컨텍스트에서 받아볼 수 있도록 리스너를 등록한다.
		m_sensor_manager.registerListener(this, m_sensor,SensorManager.SENSOR_DELAY_UI);
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

	void countDownStart(int choiceNb) {
		final int index = choiceNb - 1;
		countShow = count[index];
		fixedNum1.setText("상체 " + arr[index][0]);
		fixedNum2.setText(" 복부 " + arr[index][1]);
		fixedNum3.setText(" 하체R " + arr[index][2]);
		fixedNum4.setText(" 하체L " + arr[index][3]);
		countTxt.setText(String.valueOf(countShow) + "초");

		countDownTimer = new CountDownTimer(count[index] * 1000,
				COUNT_DOWN_INTERVAL) {
			public void onTick(long millisUntilFinished) {
				playCheck = 1;
				count[index]--;
				countTxt.setText(String.valueOf(count[index]) + "초");
			}

			public void onFinish() {
				playCheck = 2;
				countTxt.setText(String.valueOf("도전실패"));
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