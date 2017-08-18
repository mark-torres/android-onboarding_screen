package com.hsoftmobile.onboardingscreendemo;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class OnboardingActivity extends AppCompatActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	private ArgbEvaluator evaluator = new ArgbEvaluator();

	private ImageView[] indicators;

	private int currentPage;

	private Context thisActivity;

	private static int[] images = new int[]{
			R.drawable.city_02,
			R.drawable.city_03,
			R.drawable.city_04
	};
	private static String[] titles = new String[]{
			"Step 1",
			"Step 2",
			"Step 3",
	};
	private static String[] texts = new String[]{
			"Maecenas faucibus mollis interdum.",
			"Integer posuere erat a ante venenatis dapibus posuere velit aliquet.",
			"Sed posuere consectetur est at lobortis."
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onboarding);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		currentPage = 0;
		thisActivity = this;

		// bind widgets
		Button introBtnSkip = (Button) findViewById(R.id.intro_btn_skip);
		indicators = new ImageView[]{
				(ImageView) findViewById(R.id.page_indicator_0),
				(ImageView) findViewById(R.id.page_indicator_1),
				(ImageView) findViewById(R.id.page_indicator_2)
		};
		final ImageButton introBtnNext = (ImageButton) findViewById(R.id.intro_btn_next);
		final Button introBtnFinish = (Button) findViewById(R.id.intro_btn_finish);

		introBtnSkip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		introBtnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				currentPage++;
				mViewPager.setCurrentItem(currentPage, true);
			}
		});

		introBtnFinish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
				// save preference
				Common.saveSharedSetting(thisActivity, Common.PREF_USER_FIRST_TIME, "false");
			}
		});

		// page update
		final int[] colorList = new int[]{
				ContextCompat.getColor(this, R.color.cyan),
				ContextCompat.getColor(this, R.color.orange),
				ContextCompat.getColor(this, R.color.green)
		};
		final int maxPosition = colorList.length - 1;
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// color update
				int endPosition = (position == maxPosition ? position : position + 1);
				int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[endPosition]);
				mViewPager.setBackgroundColor(colorUpdate);
			}

			@Override
			public void onPageSelected(int position) {
				currentPage = position;
				updateIndicatos(currentPage);
				mViewPager.setBackgroundColor(colorList[currentPage]);
				introBtnNext.setVisibility( (position == maxPosition) ? View.GONE : View.VISIBLE );
				introBtnFinish.setVisibility( (position == maxPosition) ? View.VISIBLE : View.GONE );
			}

			@Override
			public void onPageScrollStateChanged(int state) {}
		});
	}

	private void updateIndicatos(int position) {
		for (int i = 0; i < indicators.length; i++) {
			int bgResId = (i == position) ? R.drawable.indicator_selected : R.drawable.indicator_unselected;
			indicators[i].setBackgroundResource(bgResId);
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		public PlaceholderFragment() {
		}

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_onboarding, container, false);

			int sectionIndex = getArguments().getInt(ARG_SECTION_NUMBER) - 1;

			ImageView centerImage = (ImageView) rootView.findViewById(R.id.center_image);
			TextView pageTitle = (TextView) rootView.findViewById(R.id.page_title);
			TextView pageText = (TextView) rootView.findViewById(R.id.page_text);

			centerImage.setBackgroundResource(images[sectionIndex]);
			pageTitle.setText(titles[sectionIndex]);
			pageText.setText(texts[sectionIndex]);

			return rootView;
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "SECTION";
		}
	}
}
