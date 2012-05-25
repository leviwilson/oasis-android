package com.oasisgranger.task;

public interface WorkItem<Parameter, Result> {

	Result doInBackground(Parameter...parameters);
	void onPostExecute(Result result);
}
