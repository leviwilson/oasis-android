package com.oasisgranger.test.matchers;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

public class StartedActivityMatcher extends TypeSafeMatcher<Context> {
    private final String expectedActivityClass;

    private String message;

    public StartedActivityMatcher(Class<? extends Activity> expectedActivityClass) {
        this.expectedActivityClass = expectedActivityClass.getName();
    }

    @Override
    public boolean matchesSafely(Context actualContext) {
        
        message = "to start " + expectedActivityClass + ", but ";

        final Intent actualStartedIntent = shadowOf((ContextWrapper) actualContext).getNextStartedActivity();

        if (actualStartedIntent == null) {
            message += "didn't start anything";
            return false;
        }
        
        String actualIntentClass = actualStartedIntent.getComponent().getClassName();

        boolean intentsMatch = expectedActivityClass.equals(actualIntentClass);
        
        if (!intentsMatch) {
            message += "started " + actualIntentClass;
        }
        return intentsMatch;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(message);
    }
    
    public static Matcher<Context> started(Class<? extends Activity> expectedClass) {
        return new StartedActivityMatcher(expectedClass);
    }
}
