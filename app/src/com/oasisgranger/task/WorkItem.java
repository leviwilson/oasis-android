package com.oasisgranger.task;

public interface WorkItem<Parameter, Result> {

	void onPreExecute();
	Result doInBackground(Parameter...parameters);
	void onPostExecute(Result result);
}
