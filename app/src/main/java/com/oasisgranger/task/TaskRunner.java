package com.oasisgranger.task;

public interface TaskRunner {

	public abstract <Parameter, Result> void run(WorkItem<Parameter, Result> work, Parameter... result);
}
