package com.oasisgranger.test;

import java.io.File;

import org.junit.runners.model.InitializationError;
import org.mockito.MockitoAnnotations;

import android.app.Application;
import android.content.Context;

import com.oasisgranger.OasisGrangerApp;
import com.oasisgranger.task.TaskRunner;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

public class OasisTestRunner extends RobolectricTestRunner {

	public OasisTestRunner(Class<?> testClass) throws InitializationError {
		super(testClass, new File("../app"));
	}
	
	@Override
	public Object createTest() throws Exception {
		final Object testClass =  super.createTest();
		MockitoAnnotations.initMocks(testClass);
		return testClass;
	}
	
	@Override
	protected Application createApplication() {
		final OasisGrangerApp application =  new OasisGrangerApp();
		application.configure()
			.withBinding(Context.class, Robolectric.application)
			.withBinding(TaskRunner.class, InlineTaskRunner.class);
		return application;
	}

}
