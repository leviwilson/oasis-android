package com.oasisgranger.test;

import java.io.File;

import org.junit.runners.model.InitializationError;

import com.xtremelabs.robolectric.RobolectricTestRunner;

public class OasisTestRunner extends RobolectricTestRunner {

	public OasisTestRunner(Class<?> testClass) throws InitializationError {
		super(testClass, new File("../app"));
	}

}
