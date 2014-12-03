package sm.mm.schedule_manager;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class ParseInit extends Application {

	private static ParseInit instance = new ParseInit();

	public ParseInit() {
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
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
	
	}

}