package com.oasisgranger.test;

import com.oasisgranger.task.TaskRunner;
import com.oasisgranger.task.WorkItem;

public class InlineTaskRunner implements TaskRunner {

	@Override
	public <Parameter, Result> void run(WorkItem<Parameter, Result> work, Parameter... parameters) {
		work.onPreExecute();
		work.onPostExecute(work.doInBackground(parameters));
	}

}
