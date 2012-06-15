package com.oasisgranger.di;

import java.util.ArrayList;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

public class DependencyConfigurator {
	
	private OasisModule oasisModule;
	private ArrayList<Module> overrides = new ArrayList<Module>();
	
	public DependencyConfigurator(final Context context) {
		this.oasisModule = new OasisModule(context);
	}
	
	public DependencyConfigurator withModule(OasisModule module) {
		oasisModule = module;
		return this;
	}
	
	public <T> DependencyConfigurator withBinding(final Class<T> klass, final T instance) {
		overrides.add(new AbstractModule() {
			protected void configure() {
				bind(klass).toInstance(instance);
			}
		});
		
		return this;
	}

	public <T, TOther extends T> DependencyConfigurator withBinding(final Class<T> klass, final Class<TOther> otherKlass) {
		overrides.add(new AbstractModule() {
			protected void configure() {
				bind(klass).to(otherKlass);
			}
		});
		
		return this;
	}

	public Injector getInjector() {
		Module modules = Modules.override(oasisModule).with(overrides);
		return Guice.createInjector(modules);
	}
}
