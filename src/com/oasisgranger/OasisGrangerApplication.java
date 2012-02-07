package com.oasisgranger;

import android.app.Application;

import com.google.inject.Injector;

public class OasisGrangerApplication extends Application {
	
	private Injector injector;
	private DependencyConfigurator moduleBuilder;
	
	public OasisGrangerApplication() {
		moduleBuilder = new DependencyConfigurator();
	}
	
	public DependencyConfigurator configure() {
		if( null != injector ) {
			throw new RuntimeException("Too late to bind dependencies");
		}
		
		return moduleBuilder;
	}
	
	private Injector getInjector() {
		if( null == injector ) {
			injector = moduleBuilder.getInjector();
		}
		
		return injector;
	}

	public void injectInto(Object object) {
		getInjector().injectMembers(object);
	}
}
