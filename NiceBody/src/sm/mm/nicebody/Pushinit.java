package sm.mm.nicebody;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class Pushinit extends Application {

	private int[] count;
	private static Pushinit instance = new Pushinit();

	public Pushinit() {
		instance = this;
	}

	public static Context getContext() {
		return instance;
	}
	
	public void setCount(int[] count){
        this.count = count;
    }

    public int[] getCount(){
        return count;
    }

    
	@Override
	public void onCreate() {
		count = new int[] { 30, 60, 90, 120, 180, 210, 240, 300, 330, 360 };
		super.onCreate();
		Parse.initialize(this, "hxiuvZX8btQnfinwtOaGtPbR3F7lJOFygQF3Ried",
				"AmAEA69L21vTTntYMLTInbiynU4DrsRtBGeXeuYL");
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
	
	}

}