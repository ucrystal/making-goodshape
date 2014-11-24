package sm.mm.nicebody;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.content.Intent;

public class Splash extends Activity {

	private final int SPLASH_DISPLAY_LENGTH = 2000;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        new Handler().postDelayed(new Runnable() {
            
            // Using handler with postDelayed called runnable run method
  
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, Main.class);
                startActivity(i);
                overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
  
                // close this activity
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH); // wait for 5 seconds
	}

	@Override
    protected void onDestroy() {
         
        super.onDestroy();
         
    }

}
