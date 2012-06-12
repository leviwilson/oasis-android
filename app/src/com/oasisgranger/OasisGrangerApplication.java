package com.oasisgranger;

import android.app.Application;
import android.content.Intent;

import com.google.inject.Injector;
import com.oasisgranger.di.DependencyConfigurator;

public class OasisGrangerApplication extends Application {
	
	private Injector injector;
	private DependencyConfigurator moduleBuilder;
	
	public OasisGrangerApplication() {
		moduleBuilder = new DependencyConfigurator();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		startService(new Intent(this, PodcastService.class));
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
