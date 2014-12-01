package sm.mm.nicebody;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class Pushinit extends Application {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {

		super.onCreate();

		Parse.initialize(this, "JSemUvMrzikXlTudSXUZEqpwhpJomzymZIXnMK0m",
				"g244BplyVOkZ5tZc0fkXKoDHz2SjXfC6iAXaYH8l");
		PushService.setDefaultPushCallback(this, Profile_modify.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();

	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

}
