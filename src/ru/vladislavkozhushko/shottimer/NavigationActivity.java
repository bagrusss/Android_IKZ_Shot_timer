package ru.vladislavkozhushko.shottimer;

import ru.vladislavkozhushko.fragments.AboutFragment;
import ru.vladislavkozhushko.fragments.ExFragment;
import ru.vladislavkozhushko.fragments.SettingsFragment;
import ru.vladislavkozhushko.fragments.ShotFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class NavigationActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {


	private NavigationDrawerFragment mNavigationDrawerFragment;

	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		/*new Thread(new Runnable() {			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
				NavigationActivity.this.runOnUiThread(new Runnable() {					
					@Override
					public void run() {
						if (System.currentTimeMillis()>1434671999000L)
							finish();
						Log.d("Finish", "Finish "+System.currentTimeMillis());
					}
				});
			}
		}).start();*/
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch (position) {
		case 0:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new ShotFragment()).commit();
			break;
		case 1:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new ExFragment()).commit();
			break;
		case 2:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new SettingsFragment()).commit();
			break;
		case 3:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new AboutFragment()).commit();
			break;
		case 4:
			onBackPressed();
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.navigation, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	

		return super.onOptionsItemSelected(item);
	}

}
