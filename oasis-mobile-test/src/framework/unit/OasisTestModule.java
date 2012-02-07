package framework.unit;

import com.oasisgranger.DialogFacade;
import com.oasisgranger.di.OasisModule;
import com.oasisgranger.task.TaskRunner;

public class OasisTestModule extends OasisModule {
	
	@Override
	protected void configure() {
		bindCommon();
		bindTestOnly();
	}

	private void bindTestOnly() {
		bind(DialogFacade.class).to(DialogFactoryStub.class);
		bind(TaskRunner.class).to(InlineTaskRunner.class);
	}

}
