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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.diligencedojo.boxready2.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class LogList extends Activity {
	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";
	ListView list;
	boolean expanded = false;
	int lastItem = 99;
	int itemNum = 0;
	ImageView itemDef;

	// string array from the WODListFragement class to log work outs
	String[] weekWorkout = { "Week 1Workout 1", "Week 1Workout 2",
			"Week 1Workout 3", "Week 2Workout 1", "Week 2Workout 2",
			"Week 2Workout 3", "Week 3Workout 1", "Week 3Workout 2",
			"Week 3Workout 3", "Week 4Workout 1", "Week 4Workout 2",
			"Week 4Workout 3", "Week 5Workout 1", "Week 5Workout 2",
			"Week 5Workout 3", "Week 6Workout 1", "Week 6Workout 2",
			"Week 6Workout 3", "Week 7Workout 1", "Week 7Workout 2",
			"Week 7Workout 3", "Week 8Workout 1", "Week 8Workout 2",
			"Week 8Workout 3" };

	// Section Headers
	private final static String[] weekNums = new String[] { "Week 1", "Week 2",
			"Week 3", "Week 4", "Week 5", "Week 6", "Week 7", "Week 8" };

	// Section Contents
	private final static String[] workoutNums = new String[] { "Workout 1",
			"Workout 2", "Workout 3" };

	// Adapter for ListView Contents
	private SepListAdapter adapter;

	public Map<String, ?> createItem(String title, String caption) {
		Map<String, String> item = new HashMap<String, String>();
		item.put(ITEM_TITLE, title);
		item.put(ITEM_CAPTION, caption);
		return item;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.log_main);

//		// look up the AdView as a resource and load a request
//		AdView adView = (AdView) findViewById(R.id.logAdView);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		try {
//			adView.loadAd(adRequest);
//		} catch (NoClassDefFoundError ex) {
//		}

		// Get the user's work out date and screen shot
		TinyDB tinydb = new TinyDB(getBaseContext());
		final ArrayList<String> imageList = tinydb.getList("imageList");
		ArrayList<String> dateList = new ArrayList<String>();
		dateList = tinydb.getList("dateList");
		int flag = 0;

		List<Map<String, ?>> week1 = new LinkedList<Map<String, ?>>();
		for (int a = 0; a < workoutNums.length; a++) {
			if (flag != dateList.size()) {
				week1.add(createItem(workoutNums[a], dateList.get(a)));
				flag++;
			} else
				week1.add(createItem(workoutNums[a], " "));
		}

		List<Map<String, ?>> week2 = new LinkedList<Map<String, ?>>();
		for (int a = 0; a < workoutNums.length; a++) {
			if (flag != dateList.size()) {
				week2.add(createItem(workoutNums[a], dateList.get(a + 3)));
				flag++;
			} else
				week2.add(createItem(workoutNums[a], " "));
		}

		List<Map<String, ?>> week3 = new LinkedList<Map<String, ?>>();
		for (int a = 0; a < workoutNums.length; a++) {
			if (flag != dateList.size()) {
				week3.add(createItem(workoutNums[a], dateList.get(a + 6)));
				flag++;
			} else
				week3.add(createItem(workoutNums[a], " "));
		}

		List<Map<String, ?>> week4 = new LinkedList<Map<String, ?>>();
		for (int a = 0; a < workoutNums.length; a++) {
			if (flag != dateList.size()) {
				week4.add(createItem(workoutNums[a], dateList.get(a + 9)));
				flag++;
			} else
				week4.add(createItem(workoutNums[a], " "));
		}

		List<Map<String, ?>> week5 = new LinkedList<Map<String, ?>>();
		for (int a = 0; a < workoutNums.length; a++) {
			if (flag != dateList.size()) {
				week5.add(createItem(workoutNums[a], dateList.get(a + 12)));
				flag++;
			} else
				week5.add(createItem(workoutNums[a], " "));
		}

		List<Map<String, ?>> week6 = new LinkedList<Map<String, ?>>();
		for (int a = 0; a < workoutNums.length; a++) {
			if (flag != dateList.size()) {
				week6.add(createItem(workoutNums[a], dateList.get(a + 15)));
				flag++;
			} else
				week6.add(createItem(workoutNums[a], " "));
		}

		List<Map<String, ?>> week7 = new LinkedList<Map<String, ?>>();
		for (int a = 0; a < workoutNums.length; a++) {
			if (flag != dateList.size()) {
				week7.add(createItem(workoutNums[a], dateList.get(a + 18)));
				flag++;
			} else
				week7.add(createItem(workoutNums[a], " "));
		}

		List<Map<String, ?>> week8 = new LinkedList<Map<String, ?>>();
		for (int a = 0; a < workoutNums.length; a++) {
			if (flag != dateList.size()) {
				week8.add(createItem(workoutNums[a], dateList.get(a + 21)));
				flag++;
			} else
				week8.add(createItem(workoutNums[a], " "));
		}

		// Create the ListView Adapter
		adapter = new SepListAdapter(this);

		// Add Sections (Weeks 1 - 8)
		adapter.addSection(weekNums[0], new SimpleAdapter(this, week1,
				R.layout.log_list_complex, new String[] { ITEM_TITLE,
						ITEM_CAPTION }, new int[] { R.id.list_complex_title,
						R.id.list_complex_wodDate }));
		adapter.addSection(weekNums[1], new SimpleAdapter(this, week2,
				R.layout.log_list_complex, new String[] { ITEM_TITLE,
						ITEM_CAPTION }, new int[] { R.id.list_complex_title,
						R.id.list_complex_wodDate }));
		adapter.addSection(weekNums[2], new SimpleAdapter(this, week3,
				R.layout.log_list_complex, new String[] { ITEM_TITLE,
						ITEM_CAPTION }, new int[] { R.id.list_complex_title,
						R.id.list_complex_wodDate }));
		adapter.addSection(weekNums[3], new SimpleAdapter(this, week4,
				R.layout.log_list_complex, new String[] { ITEM_TITLE,
						ITEM_CAPTION }, new int[] { R.id.list_complex_title,
						R.id.list_complex_wodDate }));
		adapter.addSection(weekNums[4], new SimpleAdapter(this, week5,
				R.layout.log_list_complex, new String[] { ITEM_TITLE,
						ITEM_CAPTION }, new int[] { R.id.list_complex_title,
						R.id.list_complex_wodDate }));
		adapter.addSection(weekNums[5], new SimpleAdapter(this, week6,
				R.layout.log_list_complex, new String[] { ITEM_TITLE,
						ITEM_CAPTION }, new int[] { R.id.list_complex_title,
						R.id.list_complex_wodDate }));
		adapter.addSection(weekNums[6], new SimpleAdapter(this, week7,
				R.layout.log_list_complex, new String[] { ITEM_TITLE,
						ITEM_CAPTION }, new int[] { R.id.list_complex_title,
						R.id.list_complex_wodDate }));
		adapter.addSection(weekNums[7], new SimpleAdapter(this, week8,
				R.layout.log_list_complex, new String[] { ITEM_TITLE,
						ITEM_CAPTION }, new int[] { R.id.list_complex_title,
						R.id.list_complex_wodDate }));

		list = (ListView) findViewById(R.id.list_journal);
		list.setAdapter(adapter);

		// Listen for Click events on list items
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long duration) {
				ToastShooter tShoot = new ToastShooter();

				if (position >= 1 && position <= 3) // week 1
					itemNum = position - 1;
				else if (position >= 5 && position <= 7) // week 2
					itemNum = position - 2;
				else if (position >= 9 && position <= 11) // week 3
					itemNum = position - 3;
				else if (position >= 13 && position <= 15) // week 4
					itemNum = position - 4;
				else if (position >= 17 && position <= 19) // week 5
					itemNum = position - 5;
				else if (position >= 21 && position <= 23) // week 6
					itemNum = position - 6;
				else if (position >= 25 && position <= 27) // week 7
					itemNum = position - 7;
				else if (position >= 29 && position <= 31) // week 8
					itemNum = position - 8;

				String path = "path"; // set path to default value
				Bitmap bmp = null;

				// assign image to appropriate list item
				if (itemNum <= imageList.size() - 1) {
					path = imageList.get(itemNum);
					bmp = loadImageFromStorage(path, itemNum);
				} else
					tShoot.displayToast("No log exists", LogList.this);

				// collapses all children other than the current selected
				// 99 is a placeholder value (can't use 0)
				if (lastItem != 99) {
					if (lastItem != -1 && itemNum != lastItem) {
						itemDef.setVisibility(View.GONE);
						expanded = false;
					}
				}
				lastItem = itemNum;

				itemDef = (ImageView) view.findViewById(R.id.screenshots);

				if (expanded == false) {
					itemDef.setImageBitmap(bmp);
					itemDef.setVisibility(View.VISIBLE);
					itemDef.setPadding(0, 10, 0, 0);
					expanded = true;
				} else {
					itemDef.setVisibility(View.GONE);
					expanded = false;
				}
			}
		});
	}

	// RETRIEVE SCREEN SHOT FROM MEMORY
	// ***********************************
	private Bitmap loadImageFromStorage(String path, Integer itemNum) {
		TinyDB tinydb = new TinyDB(getApplicationContext());
		ToastShooter tShoot = new ToastShooter();
		Bitmap b = null;

		ArrayList<String> imageAddressJpgList = new ArrayList<String>();
		imageAddressJpgList = tinydb.getList("imageAddressJpgList");

		try {
			String imageDir = imageAddressJpgList.get(itemNum);
			File f = new File(path, imageDir);
			b = BitmapFactory.decodeStream(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			tShoot.displayToast("Can't find image", LogList.this);
			e.printStackTrace();
		}
		return b;
	}

	// ALERT DIALOG METHOD
	// **********************
	public void alertDlog(String title, CharSequence msg, CharSequence posTxt,
			CharSequence negTxt) {

		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setTitle(title);
		ad.setMessage(msg);

		ad.setCancelable(false);

		// Positive button listener for dialog
		ad.setPositiveButton(posTxt, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				upgradeApp();
				dialog.dismiss();
			}
		});

		// Negative button listener for dialog
		ad.setNegativeButton(negTxt, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		AlertDialog dialog = ad.show();
		TextView messageText = (TextView) dialog.findViewById(getResources()
				.getIdentifier("alertTitle", "id", "android"));
		messageText.setGravity(Gravity.CENTER);
		dialog.show();
	}

	// ****************************************************************************************************//
	// the next three functions control the Clear Log button on the action bar
	// ****************************************************************************************************//
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.upgrade_action_bar, menu);
		menu.getItem(0).setTitle("Clear Log");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.upgrade_action_bar:
			// Log.d("upgradeApp", "pressed!");
			String title = "Warning!";
			CharSequence msg = "This will erase your progress and start you over.";
			CharSequence posTxt = "Go Ahead";
			CharSequence negTxt = "No Thanks";

			alertDlog(title, msg, posTxt, negTxt);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}// End onOptionsItemSelected

	// this will transfer the user to the upgrade screen
	private void upgradeApp() {
		// Log.d("upgradeApp", "pressed!");
		TinyDB tinydb = new TinyDB(getApplicationContext());

		ArrayList<String> imageList = new ArrayList<String>();
		ArrayList<String> imageAddressJpgList = new ArrayList<String>();
		imageAddressJpgList = tinydb.getList("imageAddressJpgList");
		imageList = tinydb.getList("imageList");

		// this is where we delete the actual screen shots since
		// if we just clear the tinydb, we will only be deleting
		// the paths and addresses but not the actual pics
		for (int i = 0; i < imageAddressJpgList.size(); i++) {
			String imageDir = imageList.get(i);
			File f = new File(imageDir, imageAddressJpgList.get(i));
			f.delete();
		}

		tinydb.clear(); // clear all saved values on upgrade
		boolean hasUpgrade = true;
		tinydb.putBoolean("hasUpgrade", hasUpgrade);

		Intent restartApp = new Intent(getApplicationContext(), Splash.class);
		startActivity(restartApp);
	}
}
