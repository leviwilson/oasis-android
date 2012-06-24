package com.oasisgranger.di;

import android.app.Activity;
import android.app.ProgressDialog;

import com.oasisgranger.DialogFacade;

public class DialogFactory implements DialogFacade {

	@Override
	public ProgressDialog createProgress(Activity activity, String message) {
		return ProgressDialog.show(activity, "", message);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void show(Activity activity, int id) {
		activity.showDialog(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void dismiss(Activity activity, int id) {
		activity.dismissDialog(id);
	}

}
