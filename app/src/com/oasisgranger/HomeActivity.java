
package com.oasisgranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.oasisgranger.helpers.ViewHelper;

public class HomeActivity extends OasisActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
        Button button = ViewHelper.findFor(this, R.id.home_btn_podcast);
        button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, PodcastsActivity.class);
				startActivity(intent);
			}
		});
        
        button = ViewHelper.findFor(this, R.id.home_btn_aboutus);
        button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		});
	}
}
