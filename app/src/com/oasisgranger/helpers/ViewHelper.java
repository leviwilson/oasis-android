package com.oasisgranger.helpers;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class ViewHelper {

	@SuppressWarnings("unchecked")
	public static <ViewType extends View> ViewType findFor(Activity activity, int id) {
		return (ViewType) activity.findViewById(id);
	}

	@SuppressWarnings("unchecked")
	public static <ViewType extends View> ViewType findFor(View view, int id) {
		return (ViewType) view.findViewById(id);
	}

	public static void clickOn(Activity activity, int id) {
		activity.findViewById(id).performClick();
	}

	public static void clickOn(View view, int id) {
		view.findViewById(id).performClick();
	}

	public static ActivityClickHandler afterClicking(final Activity activity, final int id) {
		return new ActivityClickHandler(activity, id);
	}

	public static void setTextFor(final Activity activity, final int id, final String text) {
		final TextView textView = findFor(activity, id);
		textView.setText(text);
	}

	public static void setTextFor(final View view, final int id, final String text) {
		final TextView textView = findFor(view, id);
		textView.setText(text);
	}

	public static String textOf(final Activity activity, int id) {
		final TextView textView = findFor(activity, id);
		return textView.getText().toString();
	}

	public static String textOf(final View view, int id) {
		final TextView textView = findFor(view, id);
		return textView.getText().toString();
	}

	public static boolean isEnabled(final Activity activity, int id) {
		return activity.findViewById(id).isEnabled();
	}

	public static boolean isEnabled(final View view, int id) {
		return view.findViewById(id).isEnabled();
	}

	public static void enable(final Activity activity, int id) {
		activity.findViewById(id).setEnabled(true);
	}

	public static void enable(final View view, int id) {
		view.findViewById(id).setEnabled(true);
	}

}
