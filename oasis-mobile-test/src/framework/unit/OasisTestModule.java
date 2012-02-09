package framework.unit;

import com.google.inject.Singleton;
import com.oasisgranger.DialogFacade;
import com.oasisgranger.Requestor;
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
		bind(Requestor.class).to(RequestorStub.class).in(Singleton.class);
		bind(TaskRunner.class).to(InlineTaskRunner.class);
	}

}
