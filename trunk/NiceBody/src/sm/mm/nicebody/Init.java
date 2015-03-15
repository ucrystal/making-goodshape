package sm.mm.nicebody;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class Init extends Application {

	private int[] count;
	private static Init instance = new Init();

	public Init() {
		instance = this;
	}

	public static Context getContext() {
		return instance;
	}

	public void setCount(int[] count) {
		this.count = count;
	}

	public int[] getCount() {
		return count;
	}

	@Override
	public void onCreate() {
		count = new int[] { 50, 100, 150, 200, 250, 300, 350, 400, 450, 500 };
		super.onCreate();


	}

}