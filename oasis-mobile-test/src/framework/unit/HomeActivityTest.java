package framework.unit;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.oasisgranger.HomeActivity;
import com.oasisgranger.PodcastsActivity;
import com.oasisgranger.R;

public class HomeActivityTest extends ActivityUnitTestCase<HomeActivity> {

	public HomeActivityTest() {
		super(HomeActivity.class);
	}
	
	public void testThatWeCanNavigateToPodcasts() {
		startActivity(new Intent(), null, null);
		
		getActivity().findViewById(R.id.home_btn_podcast).performClick();
		
		assertEquals(PodcastsActivity.class.getName(), getStartedActivityIntent().getComponent().getClassName());
	}

}
