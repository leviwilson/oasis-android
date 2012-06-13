package com.oasisgranger.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;

public class ActivityClickHandler {
	private final Activity activity;
	private final int id;
	private Parcelable extra;

	public ActivityClickHandler(final Activity activity, final int id) {
		this.activity = activity;
		this.id = id;
	}

	public ActivityClickHandler with(Parcelable parcelable) {
		extra = parcelable;
		return this;
	}
	
	public void goTo(final Class<? extends Activity> klass) {
		activity.findViewById(id).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(activity, klass);
				if( null != extra ) {
					intent.putExtra(extra.getClass().getName(), extra);
				}
				activity.startActivity(intent);
			}
		});
	}
}