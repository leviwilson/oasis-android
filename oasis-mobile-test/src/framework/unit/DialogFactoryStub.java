package framework.unit;

import android.app.Activity;
import android.content.DialogInterface;

import com.oasisgranger.DialogFacade;

public class DialogFactoryStub implements DialogFacade {

	@Override
	public DialogInterface showProgressFor(Activity activity, String string) {
		return new NullDialogInterface();
	}
	
	private final class NullDialogInterface implements
			DialogInterface {
		@Override
		public void dismiss() {
		}

		@Override
		public void cancel() {
		}
	}

}
