package framework.unit;

import com.oasisgranger.AboutActivity;
import com.oasisgranger.HomeActivity;
import com.oasisgranger.PodcastsActivity;
import com.oasisgranger.R;

public class HomeActivityTest extends OasisActivityUnitTestCase<HomeActivity> {

	public HomeActivityTest() {
		super(HomeActivity.class);
	}
	
	public void testThatWeCanNavigateToPodcasts() {
		startActivityLifecycle();
		
		getActivity().findViewById(R.id.home_btn_podcast).performClick();
		assertEquals(PodcastsActivity.class.getName(), getStartedActivityIntent().getComponent().getClassName());
	}
	
	public void testThatWeCanShowTheAboutScreen() {
		startActivityLifecycle();
		
		getActivity().findViewById(R.id.home_btn_aboutus).performClick();
		assertEquals(AboutActivity.class.getName(), getStartedActivityIntent().getComponent().getClassName());
	}

}
