package com.hsoftmobile.onboardingscreendemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// check for first launch
		boolean isFirstLaunch = Boolean.valueOf(Common.readSharedSetting(this, Common.PREF_USER_FIRST_TIME, "true"));
		if (isFirstLaunch) {
			Intent introIntent = new Intent(this, OnboardingActivity.class);
			startActivity(introIntent);
		}
	}
}
