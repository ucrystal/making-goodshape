package sm.mm.schedule_manager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Developer_mail extends Activity {

	Button send_btn;
	Toast toast;
	EditText mailAdd, mailSub, mailCon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.developer_mail);

		customActionBar();

		mailAdd = (EditText) findViewById(R.id.editMail);
		mailSub = (EditText) findViewById(R.id.editSubject);
		mailCon = (EditText) findViewById(R.id.editContext);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.developer_mail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, Main.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.action_ok:

			if (android.os.Build.VERSION.SDK_INT > 9) {

				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();

				StrictMode.setThreadPolicy(policy);

			}

			String add = mailAdd.getText().toString();
			String sub = mailSub.getText().toString();
			String con = mailCon.getText().toString();

			Mail m = new Mail("dptmf762@gmail.com", "wjdwlgns");

			String[] toArr = { "dptmf762@hanmail.net" };
			m.setTo(toArr);
			m.setFrom(add);
			m.setSubject(sub);
			m.setBody(con);

			try {
				// m.addAttachment("/sdcard/DCIM/100LGDSC/CAM00001.jpg"); //
				// replace with the file from [A]

				if (m.send()) {
					Toast.makeText(Developer_mail.this,
							"Email was sent successfully.", Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(Developer_mail.this, "Email was not sent.",
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Toast.makeText(Developer_mail.this,
						"There was a problem sending the email.",
						Toast.LENGTH_LONG).show();
				Log.e("MailApp", "Could not send email", e);
			}

			/*
			 * toast = Toast.makeText(getApplicationContext(),
			 * "문의 메일이 성공적으로 보내졌습니다!", Toast.LENGTH_LONG); toast.show();
			 */
			intent = new Intent(Developer_mail.this, Main.class);
			startActivity(intent);
			overridePendingTransition(R.anim.default_start_enter,
					R.anim.default_start_exit);
			finish();
			
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
		textviewTitle.setText(R.string.title_activity_developer_mail);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
		abar.setHomeButtonEnabled(true);
	}
}
