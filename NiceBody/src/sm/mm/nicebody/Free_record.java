package sm.mm.nicebody;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;

public class Free_record extends Activity implements SensorEventListener {

	//백버튼 두번 누르면 종료 객체
	private BackPressCloseHandler backPressCloseHandler;
	
	// 센서 관련 객체
	SensorManager m_sensor_manager;
	Sensor m_sensor;

	// 크로노미터 객체
	private Chronometer ch;

	TextView free_countNum;
	int printNum = 0;
	int playCheck = 0;

	static int countResult = 0;
	static String timerResult = null;

	Button free_finish_btn;
	Button free_play_btn;
	Button free_pause_btn;
	Button free_refresh_btn;

	Sound mSound;
	long timeWhenStopped = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.free_record);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		backPressCloseHandler = new BackPressCloseHandler(this);
		
		ch = (Chronometer)findViewById(R.id.chronometer_record);
		mSound = new Sound(this, R.raw.sound);
		
		free_finish_btn = (Button) findViewById(R.id.free_finish_btn);
		free_finish_btn.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				
				if (countResult == 0) {

					Toast toast = Toast.makeText(getApplicationContext(), "운동을 하지 않았습니다ㅠㅠ",
							Toast.LENGTH_LONG);
					toast.show();
					return;
					
				}
				//현재 측정된 시간을 다음 페이지로 전달해줌
				timerResult = ch.getText().toString();
				
				Intent intent = new Intent(Free_record.this,Free_result.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
				finish();
			}
		});
		
		//측정이 가능한 상태는 playCheck가 1일때,
		//측정이 일시정지되어 있는 상태는 playCheck가 2일때 (일시정지 버튼을 누르면 측정이 중지됨)
		free_play_btn = (Button) findViewById(R.id.free_play_btn);
		free_play_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playCheck = 1;
				
				ch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
				ch.start();

				
				ch.setOnChronometerTickListener(new OnChronometerTickListener() {
					@Override
					public void onChronometerTick(Chronometer chronometer) {
						String cur_time = ch.getText().toString();
						if(cur_time.equals("05:00")) ch.stop();
					}
				});
			}
		});
		
		free_pause_btn = (Button) findViewById(R.id.free_pause_btn);
		free_pause_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playCheck = 2;
				
				timeWhenStopped = ch.getBase() - SystemClock.elapsedRealtime();
				ch.stop();

			}
		});
		
		free_refresh_btn = (Button) findViewById(R.id.free_refresh_btn);
		free_refresh_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				printNum = 0;
				free_countNum.setText("0" + printNum);
				playCheck = 2;
				
				ch.setBase(SystemClock.elapsedRealtime());
				timeWhenStopped = 0;
				ch.stop();
			}
		});


		// 출력용 텍스트뷰를 얻는다.
		free_countNum = (TextView) findViewById(R.id.countNum);

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

		long currentTime = System.currentTimeMillis();
		long lastTime = 0;
		long gabOfTime = (currentTime - lastTime);
		float testNum = 0;

		if (gabOfTime > 100) {
			lastTime = currentTime;
			testNum = event.values[0];

			if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

				if (playCheck == 1) {
					if (testNum == 0) {

						printNum++;
						countResult = printNum;

						mSound.play();
						if (printNum < 10)
							free_countNum.setText("0" + printNum);
						else
							free_countNum.setText("" + printNum);

					} else if (testNum != 0) {

						if (printNum < 10)
							free_countNum.setText("0" + printNum);
						else
							free_countNum.setText("" + printNum);
					}
				} else if (playCheck == 2) {
					return;
				}
			}
		}

	}	

	@Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
