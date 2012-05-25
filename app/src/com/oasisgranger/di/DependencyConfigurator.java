package com.oasisgranger.di;

import java.util.ArrayList;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

public class DependencyConfigurator {
	
	private OasisModule oasisModule;
	private ArrayList<Module> overrides = new ArrayList<Module>();
	
	public DependencyConfigurator() {
		this.oasisModule = new OasisModule();
	}
	
	public DependencyConfigurator withModule(OasisModule module) {
		oasisModule = module;
		return this;
	}
	
	public <T> DependencyConfigurator withBinding(final Class<T> klass, final T instance) {
		overrides.add(new AbstractModule() {
			
			@Override
			protected void configure() {
				bind(klass).toInstance(instance);
			}
		});
		
		return this;
	}

	public Injector getInjector() {
		Module modules = Modules.override(oasisModule).with(overrides);
		return Guice.createInjector(modules);
	}
}
