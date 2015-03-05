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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Recommend_record3 extends Activity implements SensorEventListener {
	private static final int MILLISINFUTURE = 50 * 1000;
	private static final int COUNT_DOWN_INTERVAL = 1000;
	private int[] count = { 10, 20, 30, 40,	60, 80 };
	private int countShow;
	int[][] arr = { { 2, 2, 3 }, { 4, 5, 8 }, { 6, 8, 13 }, { 8, 11, 18 },
			{ 10, 14, 23 }, { 12, 17, 28 } };

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

	private TextView fixedNum1, fixedNum2, fixedNum3;

	Button rec_sound_btn3;
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
									Intent intent = new Intent(Recommend_record3.this,
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
		setContentView(R.layout.recommend_record3);

		ActionBar actionBar = getActionBar();
		actionBar.hide();

		mSound = new Sound(this, R.raw.sound);
		
		countTxt = (TextView) findViewById(R.id.countTxt3);
		
		fixedNum1 = (TextView) findViewById(R.id.fixedNum1);
		fixedNum2 = (TextView) findViewById(R.id.fixedNum2);
		fixedNum3 = (TextView) findViewById(R.id.fixedNum3);
		
		if (Recommend_list.choiceCh == 1){
			countDownStart(1);
		}else if (Recommend_list.choiceCh == 2){
			countDownStart(2);
		}else if (Recommend_list.choiceCh == 3){
			countDownStart(3);
		}else if (Recommend_list.choiceCh == 4){
			countDownStart(4);
		}else if (Recommend_list.choiceCh == 5){
			countDownStart(5);			
		}else if (Recommend_list.choiceCh == 6){
			countDownStart(6);
		}
		
		rec_sound_btn3 = (Button) findViewById(R.id.button_sound_r3);
		if(Free_record.sound_ch%2 == 1){
			rec_sound_btn3.setSelected(true);
		}else if(Free_record.sound_ch%2 == 0){
			rec_sound_btn3.setSelected(false);
		}
		rec_sound_btn3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Free_record.sound_ch += 1;
				
				if(Free_record.sound_ch%2 == 1){
					rec_sound_btn3.setSelected(true);
				}else if(Free_record.sound_ch%2 == 0){
					rec_sound_btn3.setSelected(false);
				}
				
				
			}
		});
		
		

		start_btn = (Button) findViewById(R.id.recommend_play_btn3);
		start_btn.setClickable(true);
		start_btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				countDownTimer.start();
				start_btn.setClickable(false);
				
			}
		});

		giveup_btn = (Button) findViewById(R.id.recommend_finish_btn3);
		giveup_btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				countDownTimer.cancel();

				Intent intent = new Intent(Recommend_record3.this,
						Recommend_fail.class);
				startActivity(intent);
				finish();
			}
		});

		// ��¿� �ؽ�Ʈ�並 ��´�.
		recommend_countNum = (TextView) findViewById(R.id.recommendcountNum3);

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

						if(Free_record.sound_ch%2 == 0){
							mSound.play();
						}
						
						if (printNum < 10)
							recommend_countNum.setText("0" + printNum);
						else
							recommend_countNum.setText("" + printNum);
						
						if (Recommend_list.choiceCh == 1) {
							// ��ü� ���� �� ��ü� �������� �̵�
							challengeSuccess(1);
						} else if (Recommend_list.choiceCh == 2) {
							challengeSuccess(2);
						} else if (Recommend_list.choiceCh == 3) {
							challengeSuccess(3);
						} else if (Recommend_list.choiceCh == 4) {
							challengeSuccess(4);
						} else if (Recommend_list.choiceCh == 5) {
							challengeSuccess(5);
						} else if (Recommend_list.choiceCh == 6) {
							challengeSuccess(6);
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
		final int index = choiceNb-1;
		countShow = count[index];
		fixedNum1.setText("��ü " + arr[index][0]);
		fixedNum2.setText(" ���� " + arr[index][1]);
		fixedNum3.setText(" ��ü " + arr[index][2]);
		countTxt.setText(String.valueOf(countShow) + "��");
		
		countDownTimer = new CountDownTimer(count[index]*1000, COUNT_DOWN_INTERVAL) {
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
		int index = choiceNb-1;
		if (printNum == arr[index][2]) {
			recommend_countNum.setText("0" + printNum);
			countDownTimer.cancel();
			Intent intent = new Intent(Recommend_record3.this,Recommend_success.class);
			startActivity(intent);
			overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
			finish();
		}
	}
}
