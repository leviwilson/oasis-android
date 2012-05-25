package com.oasisgranger.helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ActivityClickHandler {
	private final Activity activity;
	private final int id;

	public ActivityClickHandler(final Activity activity, final int id) {
		this.activity = activity;
		this.id = id;
	}
	
	public void goTo(final Class<? extends Activity> klass) {
		activity.findViewById(id).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				activity.startActivity(new Intent(activity, klass));
			}
		});
	}
}