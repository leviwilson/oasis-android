package com.oasisgranger;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.oasisgranger.test.OasisTestRunner;

import android.content.Intent;

@RunWith(OasisTestRunner.class)
public class OasisServiceTest {
	
	@Test(expected = IllegalStateException.class)
	public void itCannotBeUsedDirectly() {
		new OasisService().onBind(new Intent());
	}
}
