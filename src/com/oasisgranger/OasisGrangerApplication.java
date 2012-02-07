package com.oasisgranger;

import com.google.inject.Guice;
import com.google.inject.Injector;

import android.app.Application;

public class OasisGrangerApplication extends Application {
	
	private Injector injector;
	private OasisModule oasisModule = new OasisModule();
	
	public <T> T instanceOf(Class<T> klass) {
		return getInjector().getInstance(klass);
	}
	
	private Injector getInjector() {
		if( null == injector ) {
			injector = Guice.createInjector(oasisModule);
		}
		
		return injector;
	}

	public void injectInto(Object object) {
		getInjector().injectMembers(object);
	}
}
