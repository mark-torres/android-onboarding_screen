package com.hsoftmobile.onboardingscreendemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by markushi on 8/18/17.
 */

public class Common {
	private static final String PREF_FILENAME = "app_settings";
	public static final String PREF_USER_FIRST_TIME = "first_launch";

	public static String readSharedSetting(Context context, String key, String defaultValue) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE);
		return preferences.getString(key, defaultValue);
	}

	public static void saveSharedSetting(Context context, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.apply();
	}
}
