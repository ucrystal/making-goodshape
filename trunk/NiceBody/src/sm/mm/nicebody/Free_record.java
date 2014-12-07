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

	//���ư �ι� ������ ���� ��ü
	private BackPressCloseHandler backPressCloseHandler;
	
	// ���� ���� ��ü
	SensorManager m_sensor_manager;
	Sensor m_sensor;

	// ũ�γ���� ��ü
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

					Toast toast = Toast.makeText(getApplicationContext(), "��� ���� �ʾҽ��ϴ٤Ф�",
							Toast.LENGTH_LONG);
					toast.show();
					return;
					
				}
				//���� ������ �ð��� ���� �������� ��������
				timerResult = ch.getText().toString();
				
				Intent intent = new Intent(Free_record.this,Free_result.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
				finish();
			}
		});
		
		//������ ������ ���´� playCheck�� 1�϶�,
		//������ �Ͻ������Ǿ� �ִ� ���´� playCheck�� 2�϶� (�Ͻ����� ��ư�� ������ ������ ������)
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


		// ��¿� �ؽ�Ʈ�並 ��´�.
		free_countNum = (TextView) findViewById(R.id.countNum);

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
