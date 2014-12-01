package sm.mm.nicebody;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class Pushinit extends Application {

	private static Pushinit instance = new Pushinit();

	public Pushinit() {
		instance = this;
	}

	public static Context getContext() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "JSemUvMrzikXlTudSXUZEqpwhpJomzymZIXnMK0m",
				"g244BplyVOkZ5tZc0fkXKoDHz2SjXfC6iAXaYH8l");
		//PushService.setDefaultPushCallback(this, Profile_modify.class);
		//PushService.subscribe(this, "push", Profile_modify.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
	
	}

}
