package sm.mm.nicebody;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Free_explain extends Activity {

	Button free_explain_next_btn; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(Free_menu.choiceEx == 1)
			setContentView(R.layout.free_pushup_explain);
		else if(Free_menu.choiceEx == 2)
			setContentView(R.layout.free_lunge_explain);
		else if(Free_menu.choiceEx == 3)
			setContentView(R.layout.free_leg_explain);

		
		free_explain_next_btn = (Button)findViewById(R.id.free_explain_next_btn);
		free_explain_next_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            Intent intent = new Intent(Free_explain.this, Free_record.class);
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

}
