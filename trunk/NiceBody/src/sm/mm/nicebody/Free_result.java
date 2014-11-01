package sm.mm.nicebody;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Free_result extends Activity {

	Button result_pushup_gomain;
	TextView free_resultNum;
	TextView free_resultTimer;
	TextView free_resultCal;
	double calCalorie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.free_result);

		customActionBar();

		free_resultNum = (TextView) findViewById(R.id.resultNum);
		free_resultTimer = (TextView) findViewById(R.id.resultTimer);
		free_resultCal = (TextView) findViewById(R.id.resultCal);

		if (Free_record.countResult < 10)
			free_resultNum.setText("0" + Free_record.countResult);
		else
			free_resultNum.setText("" + Free_record.countResult );
		
		free_resultTimer.setText( Free_record.timerResult + " 소요" );
		
		
		if(Free_menu.choiceEx == 1){
			
			/*
			//choiceEx 1은 pushUp
			calCalorie = Double.parseDouble(Profile_modify.Height)/100*Double.parseDouble(Profile_modify.Weight)*(0.0494)*2*Free_record.countResult;
			free_resultCal.setText( (int)calCalorie + " 칼로리 소모" );
			*/
			
			calCalorie = Calorie.cal_fushUp(Free_record.countResult);
			free_resultCal.setText( (int)calCalorie + " 칼로리 소모" );
			
		}else if(Free_menu.choiceEx == 2){
			
			/*
			//choiceEx 2는 lunge
			calCalorie = Double.parseDouble(Profile_modify.Height)/100*Double.parseDouble(Profile_modify.Weight)*(0.07639)*2*Free_record.countResult;
			free_resultCal.setText((int)calCalorie + " 칼로리 소모");
			*/
			
			calCalorie = Calorie.cal_lunge(Free_record.countResult);
			free_resultCal.setText( (int)calCalorie + " 칼로리 소모" );
			
			
		}else if(Free_menu.choiceEx == 3){
			
			/*
			//choiceEx 3은 legRaise
			calCalorie = Double.parseDouble(Profile_modify.Height)/100*Double.parseDouble(Profile_modify.Weight)*(0.0211)*2*Free_record.countResult;
			free_resultCal.setText((int)calCalorie + " 칼로리 소모");
			
>>>>>>> .r18
			*/
			
			calCalorie = Calorie.cal_legRaise(Free_record.countResult);
			free_resultCal.setText( (int)calCalorie + " 칼로리 소모" );
			
		}

		result_pushup_gomain = (Button) findViewById(R.id.result_gomain);
		result_pushup_gomain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Free_result.this, Main.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, Main.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.action_profile:
			intent = new Intent(this, Profile.class);
			startActivity(intent);
			break;

		case R.id.action_schedule:
			intent = new Intent(this, Schedule_calendar.class);
			startActivity(intent);
			break;

		case R.id.action_settings:
			intent = new Intent(this, Main.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void customActionBar() {
		// Customize the ActionBar
		final ActionBar abar = getActionBar();
		abar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#67C6E5")));
		// abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));//line
		// under the action bar
		View viewActionBar = getLayoutInflater().inflate(
				R.layout.actionbar_layout, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				// Center the textview in the ActionBar !
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		TextView textviewTitle = (TextView) viewActionBar
				.findViewById(R.id.actionbar_textview);
		textviewTitle.setText(R.string.title_activity_free_success);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
		abar.setHomeButtonEnabled(true);
	}

}
