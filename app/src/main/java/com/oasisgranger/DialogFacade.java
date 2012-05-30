package com.oasisgranger;

import android.app.Activity;
import android.content.DialogInterface;

public interface DialogFacade {

	DialogInterface showProgressFor(Activity activity, String string);

}
