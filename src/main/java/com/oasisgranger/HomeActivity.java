
package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.afterClicking;
import android.os.Bundle;

import com.oasisgranger.R.id;

public class HomeActivity extends OasisActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		afterClicking(this, id.home_btn_podcast).goTo(PodcastsActivity.class);
		afterClicking(this, id.home_btn_aboutus).goTo(AboutActivity.class);
	}
}
