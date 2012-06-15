package com.oasisgranger;

import android.app.Application;

import com.google.inject.Injector;
import com.oasisgranger.di.DependencyConfigurator;

public class OasisGrangerApp extends Application {
	
	private Injector injector;
	private DependencyConfigurator moduleBuilder;
	
	public OasisGrangerApp() {
		moduleBuilder = new DependencyConfigurator(this);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
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

	public <T> T instanceOf(Class<T> klass) {
		return getInjector().getInstance(klass);
	}
}
