package com.oasisgranger.di;

import android.app.Activity;
import android.app.ProgressDialog;

import com.oasisgranger.DialogFacade;

public class DialogFactory implements DialogFacade {

	@Override
	public ProgressDialog createProgress(Activity activity, String message) {
		final ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage(message);
		return progressDialog;
	}

	@Override
	public void show(Activity activity, int id) {
		activity.showDialog(id);
	}

	@Override
	public void dismiss(Activity activity, int id) {
		activity.dismissDialog(id);
	}

}
