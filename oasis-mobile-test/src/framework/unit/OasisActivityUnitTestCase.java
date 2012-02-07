package framework.unit;

import com.oasisgranger.OasisGrangerApplication;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

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
		T activity = startActivity(new Intent(), null, null);
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnPostCreate(activity, null);
        return activity;
	}

}
