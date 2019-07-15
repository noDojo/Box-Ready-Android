/**
 *  Copyright 2015 noDojo
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 *
 */
package com.diligencedojo.boxready2;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ExplFrag extends Fragment {
	private static final int REQUEST_NEW_MOVEMENT = 0;
	private ArrayList<Integer> movements = new ArrayList<Integer>();

	boolean lazyUser, skillDay, presetWod, isRandom = false;
	boolean hasUpgrade = true;
	Integer progPoint, weekNum, stopTime;
	long numDaysSinceLastWod;
	Button toLogBtn, termsBtn, buildWodBtn, menuBtn;
	String wodDefaultStr, lastWod;
	String upgradeToUse = "Upgrade to use this feature!";
	TextView currentWod;
	String[] repsAdj;
	ImageView numLeft, wodsLeftText, lastWodGraphic;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {

		// inflate explanation layout
		View v = inflater.inflate(R.layout.explanation, parent, false);

		// remove title from action bar
		getActivity().getActionBar().setDisplayShowTitleEnabled(false);

//		// Look up the AdView as a resource and load a request.
//		AdView adView = (AdView) v.findViewById(R.id.explAdView);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		try {
//			adView.loadAd(adRequest);
//		} catch (NoClassDefFoundError ex) {
//		}

		// Intent i = getActivity().getIntent();
		// repsAdj = i.getExtras().getStringArray("repsAdj");

		toLogBtn = (Button) v.findViewById(R.id.to_log_button);
		termsBtn = (Button) v.findViewById(R.id.terminology_button);
		buildWodBtn = (Button) v.findViewById(R.id.skip_to_wod);
		currentWod = (TextView) v.findViewById(R.id.todays_wod);
		numLeft = (ImageView) v.findViewById(R.id.wods_left);
		wodsLeftText = (ImageView) v.findViewById(R.id.wods_left_text);
		lastWodGraphic = (ImageView) v.findViewById(R.id.last_wod);

		// retrieve saved values from internal storage
		TinyDB tinydb = new TinyDB(v.getContext());
		tinydb.remove("timerRunning");
		boolean timerRunning = false;
		tinydb.putBoolean("timerRunning", timerRunning);
		ArrayList savedRepArray = tinydb.getList("savedRepArray");
		// change ArrayList to array
		repsAdj = new String[savedRepArray.size()];
		savedRepArray.toArray(repsAdj);
		progPoint = tinydb.getInt("progPoint");
		// progPoint = 24; // test value
		weekNum = tinydb.getInt("weekNum");
		int workoutNum = tinydb.getInt("workoutNum");
		ArrayList<String> dateList = new ArrayList<String>();
		dateList = tinydb.getList("dateList");

		// if it's the first work out, TinyDB will return 0;
		// change 0 to 1 for the display
		if (weekNum == 0)
			weekNum = 1;
		tinydb.putInt("weekNum", weekNum);
		if (workoutNum == 0)
			workoutNum = 1;
		tinydb.putInt("workoutNum", workoutNum);

		if (progPoint == 24) { // display start over/random WOD dialog
			String title = getString(R.string.done_dialog_title);
			CharSequence msg = getString(R.string.done_dialog_msg);
			CharSequence posTxt = "Start Over";
			// CharSequence negTxt = "Random WOD";
			CharSequence placeholder = "placeholder";

			alertDlog(title, msg, posTxt, placeholder);
		}

		// set the appropriate background number
		setNumLeft();

		// display today's work out
		Movement m = new Movement();

		if (progPoint < 24) {
			currentWod.setText(m.wodStylesUpgraded[progPoint]);
			currentWod.setTypeface(null, Typeface.BOLD);
			currentWod.setGravity(Gravity.CENTER_VERTICAL
					| Gravity.CENTER_HORIZONTAL);
		}

		// CHECK DATE SINCE LAST USE
		// ****************************
		lastWod = "lastWod empty...";
		// get date of last work out if upgraded
		if (hasUpgrade && !dateList.isEmpty()) {
			lastWod = dateList.get(dateList.size() - 1);
			// lastWod = "08:58PM - July 6 2014"; // test value

			// get today's date
			Date today = Calendar.getInstance().getTime();
			// declare variable for date of last work out
			Date lastWodDate = null;
			// format date
			SimpleDateFormat formatter = new SimpleDateFormat(
					"hh:mma - MMMM dd yyyy");
			try {
				lastWodDate = formatter.parse(lastWod);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			numDaysSinceLastWod = daysBetween(lastWodDate, today);

			// if it's been longer than a week since the last work out,
			// display alert dialog suggesting the user start over
			if (numDaysSinceLastWod > 7) {
				lazyUser = true;

				String title = getString(R.string.disuse_dialog_title);
				CharSequence msg = getString(R.string.disuse_dialog_msg);
				CharSequence posTxt = "Start Over";
				CharSequence negTxt = "Nah";

				alertDlog(title, msg, posTxt, negTxt);
			}
		}

		if (progPoint < 24) {
			// check if today's WOD is preset
			presetWod = isPresetWod(progPoint);
			// check if today's WOD is skill work
			skillDay = isSkillWod(progPoint);
		}

		// GO TO LOG BUTTON
		// *******************
		toLogBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent toLog = new Intent(v.getContext(), LogList.class);
				startActivity(toLog);
			}
		});

		// TERMINOLOGY BUTTON
		// *********************
		termsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent toTerm = new Intent(v.getContext(), TermAct.class);
				startActivity(toTerm);
			}
		});

		// BUILD WORKOUT BUTTON
		// ***********************
		buildWodBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectWodType(presetWod, skillDay, progPoint, isRandom);
			}
		});

		setHasOptionsMenu(true);

		return v;
	} // end onCreatView()

	// ALERT DIALOG METHOD
	// **********************
	@SuppressWarnings("deprecation")
	public void alertDlog(String title, CharSequence msg, CharSequence posTxt,
			CharSequence negTxt) {

		AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
		ad.setTitle(title);
		ad.setMessage(msg);
		ad.setCancelable(false);

		// Positive button listener for dialog
		ad.setButton(posTxt, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				upgradeApp();
				dialog.dismiss();
			}
		});

		if (negTxt != "placeholder") {
			// Negative button listener for dialog
			ad.setButton2(negTxt, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					if (hasUpgrade == true && lazyUser == false)
						randomSelected();

					dialog.dismiss();
				}
			});
		}
		ad.show();

		// center title of dialog
		TextView titleView = (TextView) ad.findViewById(getResources()
				.getIdentifier("alertTitle", "id", "android"));
		if (titleView != null)
			titleView.setGravity(Gravity.CENTER);
	}

	// DIRECT NAVIGATION BASED ON WORKOUT TYPE
	// ******************************************
	private void selectWodType(boolean presetWod, boolean skillDay,
			int progPoint, boolean isRandom) {

		if (presetWod == true) {
			Intent movementIntent = new Intent(getActivity()
					.getApplicationContext(), WODListAct.class);

			// this keeps the list view in WODListFrag from adding a
			// list item each time the user go back and forth between
			// ExplFrag and WODListFrag
			if (movements.size() == 0)
				movements.add(77); // Add flag to array **77 IS FLAG**

			movementIntent.putExtra(WODListFrag.NEW_MOVE_ADDED, movements);
			movementIntent.putExtra("presetWod", presetWod);
			movementIntent.putExtra("progPoint", progPoint);
			movementIntent.putExtra("isRandom", isRandom);
			movementIntent.putExtra(WODListFrag.REPS_ADJ, repsAdj);
			startActivityForResult(movementIntent, REQUEST_NEW_MOVEMENT);

		} else if (skillDay == true) {
			Intent movementIntent = new Intent(getActivity()
					.getApplicationContext(), WODListAct.class);

			// this keeps the list view in WODListFrag from adding a
			// list item each time the user go back and forth between
			// ExplFrag and WODListFrag
			if (movements.size() == 0)
				movements.add(99); // Add flag to array **99 IS FLAG**

			movementIntent.putExtra("progPoint", progPoint);
			movementIntent.putExtra("skillDay", skillDay);
			movementIntent.putExtra("isRandom", isRandom);
			movementIntent.putExtra(WODListFrag.NEW_MOVE_ADDED, movements);
			movementIntent.putExtra(WODListFrag.REPS_ADJ, repsAdj);
			startActivityForResult(movementIntent, REQUEST_NEW_MOVEMENT);

		} else { // user gets to choose the movements in their WOD
			Intent toMovSel = new Intent(getActivity().getApplicationContext(),
					MoveSelAct.class);
			toMovSel.putExtra("repsAdj", repsAdj);
			toMovSel.putExtra("isRandom", isRandom);
			toMovSel.putExtra("progPoint", progPoint);
			startActivity(toMovSel);
		}
	}

	// CHECK IF CURRENT WOD IS SKILL WOD
	// ************************************
	private boolean isSkillWod(int progPoint) {
		boolean skillDay;

		if (progPoint == 5 || progPoint == 10 || progPoint == 14
				|| progPoint == 19) {
			// set that it's a skill day
			skillDay = true;
		} else
			skillDay = false;

		return skillDay;
	}

	// CHECK IF CURRENT WOD IS PRESET WOD
	// *************************************
	private boolean isPresetWod(int tempProgPoint) {
		boolean presetWod;

		// if the work out is already set, go ahead and send the intent
		// the movements and reps for that work out to WODListFragment
		if (tempProgPoint == 12 || tempProgPoint == 13 || tempProgPoint == 15
				|| tempProgPoint == 18 || tempProgPoint == 20
				|| tempProgPoint == 22 || tempProgPoint == 23) {
			// set that it's a preset wod
			presetWod = true;
		} else
			presetWod = false;

		return presetWod;
	}

	// FINDS NUMBER OF DAYS SINCE LAST WORK OUT
	// *******************************************
	public static long daysBetween(Date startDate, Date endDate) {
		Calendar sDate = getDatePart(startDate);
		Calendar eDate = getDatePart(endDate);
		long daysBetween = 0;

		while (sDate.before(eDate)) {
			sDate.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}

		return daysBetween;
	}

	// PARSES DATES FOR daysBetween METHOD
	// **************************************
	public static Calendar getDatePart(Date date) {
		Calendar cal = Calendar.getInstance(); // get calendar instance
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
		cal.set(Calendar.MINUTE, 0); // set minute in hour
		cal.set(Calendar.SECOND, 0); // set second in minute
		cal.set(Calendar.MILLISECOND, 0); // set millisecond in second

		return cal; // return the date part
	}

	// SET THE APPROPRIATE BACKGROUND
	// *********************************
	public void setNumLeft() {

		switch (progPoint) {
		case 0:
			numLeft.setBackgroundResource(R.drawable.t_four);
			break;
		case 1:
			numLeft.setBackgroundResource(R.drawable.t_three);
			break;
		case 2:
			numLeft.setBackgroundResource(R.drawable.t_two);
			break;
		case 3:
			numLeft.setBackgroundResource(R.drawable.t_one);
			break;
		case 4:
			numLeft.setBackgroundResource(R.drawable.t_zero);
			break;
		case 5:
			numLeft.setBackgroundResource(R.drawable.o_nine);
			break;
		case 6:
			numLeft.setBackgroundResource(R.drawable.o_eight);
			break;
		case 7:
			numLeft.setBackgroundResource(R.drawable.o_seven);
			break;
		case 8:
			numLeft.setBackgroundResource(R.drawable.o_six);
			break;
		case 9:
			numLeft.setBackgroundResource(R.drawable.o_five);
			break;
		case 10:
			numLeft.setBackgroundResource(R.drawable.o_four);
			break;
		case 11:
			numLeft.setBackgroundResource(R.drawable.o_three);
			break;
		case 12:
			numLeft.setBackgroundResource(R.drawable.o_two);
			break;
		case 13:
			numLeft.setBackgroundResource(R.drawable.o_one);
			break;
		case 14:
			numLeft.setBackgroundResource(R.drawable.o_zero);
			break;
		case 15:
			numLeft.setBackgroundResource(R.drawable.nine);
			break;
		case 16:
			numLeft.setBackgroundResource(R.drawable.eight);
			break;
		case 17:
			numLeft.setBackgroundResource(R.drawable.seven);
			break;
		case 18:
			numLeft.setBackgroundResource(R.drawable.six);
			break;
		case 19:
			numLeft.setBackgroundResource(R.drawable.five);
			break;
		case 20:
			numLeft.setBackgroundResource(R.drawable.four);
			break;
		case 21:
			numLeft.setBackgroundResource(R.drawable.three);
			break;
		case 22:
			numLeft.setBackgroundResource(R.drawable.two);
			break;
		case 23:
			lastWodGraphic.setVisibility(View.VISIBLE);
			wodsLeftText.setVisibility(View.GONE);
			break;
		} // end switch

	} // end setNumLeft()

	// NEXT 4 METHODS CONTROL UPGRADE & RANDOM BUTTONS ON ACTION BAR
	// ****************************************************************
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		if (hasUpgrade)
			inflater.inflate(R.menu.random_action_bar, menu);
		else
			inflater.inflate(R.menu.upgrade_action_bar, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.upgrade_action_bar:
			upgradeApp();
			return true;

		case R.id.random_action_bar:
			randomSelected();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}// End onOptionsItemSelected

	private void randomSelected() {
		ToastShooter tShoot = new ToastShooter();
		Movement m = new Movement();
		Random r = new Random();
		isRandom = true;

		int ranProgPoint = r.nextInt(24 - 1) + 0;

		tShoot.displayToast("Random wod: " + m.wodStylesUpgraded[ranProgPoint],
				getActivity(), "blue");

		// check if today's WOD is preset
		presetWod = isPresetWod(ranProgPoint);
		// check if today's WOD is skill work
		skillDay = isSkillWod(ranProgPoint);

		selectWodType(presetWod, skillDay, ranProgPoint, isRandom);
	}

	// CLEAR SAVED VALUES AND START APP OVER
	// ****************************************
	private void upgradeApp() {
		TinyDB tinydb = new TinyDB(getActivity().getBaseContext());
		ArrayList<String> imageList = new ArrayList<String>();
		ArrayList<String> imageAddressJpgList = new ArrayList<String>();
		imageAddressJpgList = tinydb.getList("imageAddressJpgList");
		imageList = tinydb.getList("imageList");

		// delete screen shots along with their associated paths and addresses
		for (int i = 0; i < imageAddressJpgList.size(); i++) {
			String path = imageList.get(i);
			String address = imageAddressJpgList.get(i);
			File f = new File(path, address);
			boolean deleted = f.delete();
			if (deleted)
				Log.d("FILE_ARRAY", "Got deleted...");
		}

		tinydb.clear(); // clear all saved values on upgrade
		hasUpgrade = true;
		tinydb.putBoolean("hasUpgrade", hasUpgrade);

		Intent restartApp = new Intent(getActivity().getBaseContext(),
				Splash.class);
		startActivity(restartApp);
	}
}
