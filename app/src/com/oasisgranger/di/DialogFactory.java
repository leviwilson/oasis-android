package com.oasisgranger.di;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.oasisgranger.DialogFacade;

public class DialogFactory implements DialogFacade {

	public DialogInterface showProgressFor(Activity activity, String message) {
		return ProgressDialog.show(activity, "", message);
	}

}
