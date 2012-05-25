package framework.unit;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import com.oasisgranger.OasisGrangerApplication;
import com.oasisgranger.Requestor;
import com.oasisgranger.Response;
import com.oasisgranger.helpers.ViewHelper;

public class OasisActivityUnitTestCase<T extends Activity> extends ActivityUnitTestCase<T> {
	private OasisGrangerApplication oasisApplication = new OasisGrangerApplication();

	public OasisActivityUnitTestCase(Class<T> activityClass) {
		super(activityClass);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		oasisApplication.configure().withModule(new OasisTestModule());
		setApplication(oasisApplication);
	}
	
	public T startActivityLifecycle() {
		return startActivityLifecycleWith(new Intent());
	}

	public T startActivityLifecycleWith(Intent intent) {
		T activity = startActivity(intent, null, null);
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnPostCreate(activity, null);
        return activity;
	}
	
	public OasisGrangerApplication getApplication() {
		return oasisApplication;
	}

	protected void setupToRespondWith(final String message) {
		RequestorStub requestor = (RequestorStub) getApplication().instanceOf(
				Requestor.class);
		requestor.setResponse(new Response(null) {
			@Override
			public String getMessage() throws IllegalStateException,
					IOException {
				return message;
			}
		});
	}

	protected String textFor(int id) {
		TextView descriptionText = ViewHelper.findFor(getActivity(), id);
		String text = descriptionText.getText().toString();
		return text;
	}

}
