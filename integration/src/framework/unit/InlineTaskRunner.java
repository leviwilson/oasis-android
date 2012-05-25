package framework.unit;

import com.oasisgranger.task.TaskRunner;
import com.oasisgranger.task.WorkItem;

public class InlineTaskRunner implements TaskRunner {

	@Override
	public <Parameter, Result> void run(WorkItem<Parameter, Result> work, Parameter... parameters) {
		work.onPostExecute(work.doInBackground(parameters));
	}

}
