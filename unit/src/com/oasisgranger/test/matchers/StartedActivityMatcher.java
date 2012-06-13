package com.oasisgranger.test.matchers;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Parcelable;

public class StartedActivityMatcher extends TypeSafeMatcher<Context> {
	private final String expectedActivityClass;
	private Extra expectedExtra;

	private String message;

	public StartedActivityMatcher(Class<? extends Activity> expectedActivityClass) {
		this.expectedActivityClass = expectedActivityClass.getName();
	}

	public StartedActivityMatcher(Class<? extends Activity> expectedActivityClass, Extra expectedExtra) {
		this.expectedActivityClass = expectedActivityClass.getName();
		this.expectedExtra = expectedExtra;
	}

	@Override
	public boolean matchesSafely(Context actualContext) {

		message = "to start " + expectedActivityClass;
		if (expectsAnExtra()) {
			message += " with extra " + expectedExtra.toString();
		}
		message += ", but ";

		final Intent actualStartedIntent = shadowOf(
				(ContextWrapper) actualContext).getNextStartedActivity();

		if (actualStartedIntent == null) {
			message += "didn't start anything";
			return false;
		}

		String actualIntentClass = actualStartedIntent.getComponent()
				.getClassName();

		boolean matched = expectedActivityClass.equals(actualIntentClass)
				&& extraExpectationMet(actualStartedIntent);

		if (!matched) {
			message += "started " + actualIntentClass;

			if (expectsAnExtra()) {
				message += " with extra " + actualExtra(actualStartedIntent);
			}

		}
		return matched;
	}

	private boolean expectsAnExtra() {
		return null != expectedExtra;
	}

	private boolean extraExpectationMet(final Intent actualStartedIntent) {
		if (!expectsAnExtra()) {
			return true;
		}

		final Parcelable actualExtra = actualExtra(actualStartedIntent);
		return expectedExtra.getExtraValue().equals(actualExtra);
	}

	private Parcelable actualExtra(final Intent actualStartedIntent) {
		return actualStartedIntent.<Parcelable> getParcelableExtra(expectedExtra.getExtraName());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(message);
	}

	public static Matcher<Context> started(Class<? extends Activity> expectedClass) {
		return new StartedActivityMatcher(expectedClass);
	}

	public static Matcher<Context> startedWithExtra(Class<? extends Activity> expectedClass, Extra expectedExtra) {
		return new StartedActivityMatcher(expectedClass, expectedExtra);
	}
}
