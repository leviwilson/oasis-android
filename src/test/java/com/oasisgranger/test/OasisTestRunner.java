package com.oasisgranger.test;

import java.io.File;

import org.junit.runners.model.InitializationError;
import org.mockito.MockitoAnnotations;

import android.app.Application;

import com.oasisgranger.OasisGrangerApplication;
import com.oasisgranger.task.TaskRunner;
import com.xtremelabs.robolectric.RobolectricTestRunner;

public class OasisTestRunner extends RobolectricTestRunner {

	public OasisTestRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}
	
	@Override
	public Object createTest() throws Exception {
		final Object testClass =  super.createTest();
		MockitoAnnotations.initMocks(testClass);
		return testClass;
	}
	
	@Override
	protected Application createApplication() {
		final OasisGrangerApplication application =  new OasisGrangerApplication();
		application.configure()
			.withBinding(TaskRunner.class, InlineTaskRunner.class);
		return application;
	}

}