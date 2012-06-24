package com.oasisgranger.di;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;

import android.app.Activity;
import android.app.Dialog;

import com.oasisgranger.test.OasisTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowAlertDialog;

@RunWith(OasisTestRunner.class)
public class DialogFactoryTest {
	
	@Spy Activity activity = new Activity() {
		protected Dialog onCreateDialog(int id) {
			return expectedDialog;
		}
	};
	
	DialogFactory dialogFactory = new DialogFactory();
	Dialog expectedDialog = new Dialog(activity);
	
	@Test
	public void itCanShowDialogs() {
		dialogFactory.show(activity, 123);
		verify(activity).showDialog(123);
	}
	
	@Test
	public void itCanDismissDialogs() {
		dialogFactory.show(activity, 123);
		dialogFactory.dismiss(activity, 123);
		verify(activity).dismissDialog(123);
	}
	
	@Test
	public void itCanCreateProgressDialogs() {
		expectedDialog = dialogFactory.createProgress(activity, "Some progress information...");
		dialogFactory.show(activity, 123);
		
		ShadowAlertDialog actualDialog = shadowOf(Robolectric.application).getLatestAlertDialog();
		assertThat(actualDialog.getMessage(), is("Some progress information..."));
	}

}
