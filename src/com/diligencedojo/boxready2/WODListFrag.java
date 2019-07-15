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
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ListFragment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WODListFrag extends ListFragment {
	public static final String NEW_MOVE_ADDED = "new_move_in_list";
	public static final String MOVE_REMOVED = "move_removed";
	public static final String REPS_ADJ = "repsAdj";

	private Handler customHandler = new Handler();
	long timeInMilliseconds, timeSwapBuff, updatedTime, startTime = 0L;
	TextView timerValue, countdownValue, userScore, wod_type, wod_explanation,
			add_move;
	EditText enter_score;
	Button saveWodBtn, explnButton, startButton, endWodButton, finalize_wod,
			delete_move, backToCongrats;
	boolean timerRunning, explainClicked, timerCountsDown, isPreset, skillDay,
			presetWod, canButtercup, emom, doButtercup, addListItem,
			scoreEntered, done, timedWod, isRandom = false;
	boolean hasUpgrade = true;
	int countdownStart = 4000;
	int decrementBy = 1000;
	int stopTime, timedWodCounter, cfCounter, progPoint, moveCount, skillIndex,
			ranWod, skillCounter, stylePos = 0;
	String picFileName = "noName";
	String currentWod, wodTimeString;
	private ArrayList<Integer> mMovements;
	String[] repsAdj;
	byte[] byteArray;

	// string array to be used in the ListSample class to log work outs
	String[] weekWorkout = { "Week 1Workout 1", "Week 1Workout 2",
			"Week 1Workout 3", "Week 2Workout 1", "Week 2Workout 2",
			"Week 2Workout 3", "Week 3Workout 1", "Week 3Workout 2",
			"Week 3Workout 3", "Week 4Workout 1", "Week 4Workout 2",
			"Week 4Workout 3", "Week 5Workout 1", "Week 5Workout 2",
			"Week 5Workout 3", "Week 6Workout 1", "Week 6Workout 2",
			"Week 6Workout 3", "Week 7Workout 1", "Week 7Workout 2",
			"Week 7Workout 3", "Week 8Workout 1", "Week 8Workout 2",
			"Week 8Workout 3" };

	// these arrays indicate what moves make up these wods
	Integer[] angie = { 1, 0, 4, 3 };
	Integer[] annie = { 7, 4 };
	Integer[] nicole = { 6, 1 };
	Integer[] loredo = { 3, 0, 8, 6 };
	Integer[] cindy = { 1, 0, 3 };
	Integer[] murph = { 6, 1, 0, 3, 6 };
	Integer[] skillArray = { 99 };

	// ****************************************************************************************************//

	/**
	 * Create a new instance of the fragment. This will allow us to use the
	 * moves passed in from MovementSelectionFragment within the
	 * WODListFragment.
	 */
	public static WODListFrag newInstance(ArrayList<Integer> moves) {
		Bundle args = new Bundle();
		args.putSerializable(NEW_MOVE_ADDED, moves);
		// Log.d("fmovements", "size: " + moves.size());
		WODListFrag fragment = new WODListFrag();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.movement_title);

		// Get the arguments that were serialized in WODListActivity.
		Bundle args = getArguments();
		mMovements = (ArrayList<Integer>) args.getSerializable(NEW_MOVE_ADDED);

		// SKILL DAY //
		if (mMovements.get(0) == 99) {
			MovementsAdapter adapter = new MovementsAdapter(mMovements);
			setListAdapter(adapter);
			adapter.setButtonsVisible(true);
		}
		// PRESET DAY //
		else if (mMovements.get(0) == 77) {
			TinyDB tinydb = new TinyDB(getActivity().getApplicationContext());
			Intent i = getActivity().getIntent();
			ranWod = i.getExtras().getInt("progPoint");
			isRandom = i.getExtras().getBoolean("isRandom");

			if (isRandom == true) {
				if (ranWod == 12)
					cfCounter = 0;
				else if (ranWod == 13 || ranWod == 22)
					cfCounter = 1;
				else if (ranWod == 15)
					cfCounter = 2;
				else if (ranWod == 18)
					cfCounter = 3;
				else if (ranWod == 20)
					cfCounter = 4;
				else if (ranWod == 23)
					cfCounter = 6;
				else
					;
			} else {
				// it's a normal wod (not random)
				// cfCounter is used to choose which array (above)
				// should be used
				cfCounter = tinydb.getInt("cfCounter");
			}

			// remove flag
			if (mMovements.size() > 0) {
				mMovements.remove(mMovements.size() - 1);

				if (cfCounter == 0)
					mMovements.addAll(Arrays.asList(angie));
				else if (cfCounter == 1 || cfCounter == 5)
					mMovements.addAll(Arrays.asList(annie));
				else if (cfCounter == 2)
					mMovements.addAll(Arrays.asList(nicole));
				else if (cfCounter == 3)
					mMovements.addAll(Arrays.asList(loredo));
				else if (cfCounter == 4)
					mMovements.addAll(Arrays.asList(cindy));
				else if (cfCounter == 6)
					mMovements.addAll(Arrays.asList(murph));
				else
					;

				MovementsAdapter adapter = new MovementsAdapter(mMovements);
				setListAdapter(adapter);
				adapter.setButtonsVisible(true);
			}
		}
		// NORMAL DAY //
		else {
			Activity activity = getActivity();
			if (activity instanceof WODListAct)
				((WODListAct) activity).setMovements(mMovements);

			MovementsAdapter adapter = new MovementsAdapter(mMovements);
			setListAdapter(adapter);
			adapter.setButtonsVisible(true);
		}
	} // end onCreate()

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {

		final View v = inflater.inflate(R.layout.wod_list, parent, false);

		// get intent info
		Intent i = getActivity().getIntent();
		repsAdj = i.getExtras().getStringArray("repsAdj");
		progPoint = i.getExtras().getInt("progPoint");
		moveCount = i.getExtras().getInt("moveCount");
		skillDay = i.getExtras().getBoolean("skillDay");
		presetWod = i.getExtras().getBoolean("presetWod");
		isRandom = i.getExtras().getBoolean("isRandom");

		// set and locate everything
		wod_type = (TextView) v.findViewById(R.id.wod_type);
		explnButton = (Button) v.findViewById(R.id.explain_button);
		wod_explanation = (TextView) v.findViewById(R.id.wod_explanation);
		startButton = (Button) v.findViewById(R.id.startButton);
		endWodButton = (Button) v.findViewById(R.id.endWodButton);
		timerValue = (TextView) v.findViewById(R.id.timerValue);
		countdownValue = (TextView) v.findViewById(R.id.countdown_value);
		add_move = (TextView) v.findViewById(R.id.add_move);
		finalize_wod = (Button) v.findViewById(R.id.finalize_wod);
		delete_move = (Button) v.findViewById(R.id.delete_move);
		saveWodBtn = (Button) v.findViewById(R.id.save_wod_button);
		enter_score = (EditText) v.findViewById(R.id.enter_score);
		userScore = (TextView) v.findViewById(R.id.score_entered);
		backToCongrats = (Button) v.findViewById(R.id.back_to_congrats_button);

		TinyDB tinydb = new TinyDB(v.getContext());
		Movement mObj = new Movement();
		ToastShooter tShoot = new ToastShooter();

		// retrieve and set upgrade state of app
		hasUpgrade = tinydb.getBoolean("hasUpgrade");
		stylePos = progPoint;

		// hide action bar
		getActivity().getActionBar().hide();

		// get WOD title
		currentWod = mObj.wodStylesUpgraded[progPoint];

		if (progPoint == 5 || progPoint == 10 || progPoint == 14
				|| progPoint == 19) {
			// set that it's a skill day
			skillDay = true;
		} else
			skillDay = false;

		// set skill/preset stuff
		if (skillDay == true || presetWod == true) {
			delete_move.setVisibility(View.GONE);
			add_move.setVisibility(View.GONE);
			finalize_wod.setVisibility(View.VISIBLE);

			getActivity().getActionBar().hide();
		}

		// set title of WOD
		wod_type.setText(currentWod);
		wod_type.setTypeface(null, Typeface.BOLD);

		// identify WODs with preset times
		if (progPoint == 2 || progPoint == 3 || progPoint == 4
				|| progPoint == 5 || progPoint == 9 || progPoint == 10
				|| progPoint == 14 || progPoint == 15 || progPoint == 16
				|| progPoint == 19 || progPoint == 20) {

			timedWod = true;
			timedWodCounter = tinydb.getInt("timedWodCounter");
			// tShoot.displayToast("timedWodCounter = " + timedWodCounter,
			// getActivity());
			// stopTime = mObj.whenToStop[timedWodCounter];
			// IDK, JUST A BUG HACK //
			if (timedWodCounter < 11)
				stopTime = mObj.whenToStop[timedWodCounter];
			else
				stopTime = 1;
		} else
			timedWod = false;

		// EXPLAIN BUTTON (labeled Expand/Hide)
		// ***************************************
		explnButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (explainClicked == false) {
					Movement m = new Movement();

					if (progPoint == 7) {
						String moveName = " "
								+ m.movementNames[mMovements.get(0)];
						wod_explanation.setText(currentWod + moveName + "\n"
								+ m.wodScoring[progPoint]);
						wod_explanation.setVisibility(View.VISIBLE);
					} else {
						wod_explanation.setText(m.wodScoring[progPoint]);
						wod_explanation.setVisibility(View.VISIBLE);
					}

					explnButton.setText("Hide");
					explnButton.setTypeface(null, Typeface.ITALIC);
					explainClicked = true;
				} else {
					wod_explanation.setVisibility(View.GONE);
					explnButton.setText("Expand");
					explnButton.setTypeface(null, Typeface.ITALIC);

					explainClicked = false;
				}
			}
		});

		// determine if WOD can be butter cupped
		if (hasUpgrade == true) {

			// center title in action bar by inflating custom layout
			ActionBar actionBar = getActivity().getActionBar();
			actionBar.setDisplayShowCustomEnabled(true);
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			actionBar
					.setCustomView(inflater.inflate(
							R.layout.action_bar_centered, null),
							new ActionBar.LayoutParams(
									ActionBar.LayoutParams.WRAP_CONTENT,
									ActionBar.LayoutParams.WRAP_CONTENT,
									Gravity.CENTER));

			// these are the wods that can be butter cupped
			if (currentWod == "3 ROUNDS FOR TIME" || currentWod == "THE FIFTY"
					|| currentWod == "ANGIE" || currentWod == "ANNIE"
					|| currentWod == "NICOLE"
					|| currentWod == "HERO WOD: LOREDO"
					|| currentWod == "CINDY" || currentWod == "HERO WOD: MURPH") {

				canButtercup = true;
			} else
				canButtercup = false;
		}

		// WHEN WOD IS FULL
		// *******************
		if (mMovements.size() == moveCount) {
			add_move.setVisibility(View.GONE);
			finalize_wod.setVisibility(View.VISIBLE);

			// hide action bar
			getActivity().getActionBar().hide();
		} // end of full

		// set boolean for emom for use in timer
		if (progPoint == 4 || progPoint == 16)
			emom = true;

		// FINALIZE BUTTON
		// ******************
		finalize_wod.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v1) {

				// butter cup feature is only available with upgrade
				if (hasUpgrade == true && canButtercup == true) {

					AlertDialog ad = new AlertDialog.Builder(getActivity())
							.create();
					ad.setTitle(R.string.buttercup_title);
					CharSequence bcMsg = getString(R.string.buttercup_msg);
					ad.setMessage(bcMsg);
					CharSequence yesBtn = "Do it";
					CharSequence noBtn = "Nah";

					ad.setCancelable(false);

					// "Yes" button listener for butter cup dialog
					ad.setButton(yesBtn, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							ToastShooter tShoot = new ToastShooter();

							// this button will need to handle cutting all
							// the reps in the WOD in half when pressed
							if (canButtercup == true) {
								// nicole and cindy times get halved when
								// butter cupped
								if (currentWod == "NICOLE"
										|| currentWod == "CINDY") {
									stopTime = 10;

									tShoot.displayToast(
											"The timer will now stop at \n10 minutes instead of 20.",
											getActivity());
								} else { // doButtercup is used in the
											// MovementsAdapter
									doButtercup = true;
									// tell adapter to initiate butter cup
									((MovementsAdapter) getListAdapter())
											.notifyDataSetChanged();
								}
							}
							dialog.dismiss();
						}
					});

					// "No" button listener for butter cup dialog
					ad.setButton2(noBtn, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					ad.show();

					// center title of dialog
					TextView titleView = (TextView) ad
							.findViewById(getResources().getIdentifier(
									"alertTitle", "id", "android"));
					if (titleView != null)
						titleView.setGravity(Gravity.CENTER);
				}

				// add list item to "Back To Back" WOD for rest minute
				if (progPoint == 3) {
					addListItem = true;
					mMovements.add(55);
					MovementsAdapter adapter = new MovementsAdapter(mMovements);
					setListAdapter(adapter);
					adapter.setButtonsVisible(true);
				}

				// change visible buttons on finalize click
				finalize_wod.setVisibility(View.GONE);
				delete_move.setVisibility(View.GONE);
				startButton.setVisibility(View.VISIBLE);
			}
		}); // end of finalize_wod setOnClickListener

		// START THE CLOCK BUTTON
		// *************************
		startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				startButton.setVisibility(View.GONE);

				TinyDB tinydb = new TinyDB(getActivity().getBaseContext());
				timerRunning = true;
				tinydb.putBoolean("timerRunning", timerRunning);

				// remove the about button from all list items
				MovementsAdapter adapter = new MovementsAdapter(mMovements);
				setListAdapter(adapter);
				adapter.setButtonsVisible(false);
				adapter.notifyDataSetChanged();

				countdown();

				// matches count down and then displays stop clock
				// button when timer starts
				new CountDownTimer(countdownStart, decrementBy) {
					public void onTick(long millisUntilFinished) {
					}

					public void onFinish() {
						timerValue.setVisibility(View.VISIBLE);
						startTime = SystemClock.uptimeMillis();

						customHandler.postDelayed(updateTimerThread, 0);

						if (timedWod == false)
							endWodButton.setVisibility(View.VISIBLE);
					}
				}.start();
			}
		});

		// STOP THE CLOCK BUTTON
		// ************************
		endWodButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v3) {

				// stop clock
				timeSwapBuff += timeInMilliseconds;
				customHandler.removeCallbacks(updateTimerThread);

				// for "DEATH BY", user must enter score after
				// stopping the clock
				if (progPoint == 7)
					enterScoreDialog(currentWod);

				wod_explanation.setVisibility(View.GONE);
				startButton.setVisibility(View.GONE);
				endWodButton.setVisibility(View.GONE);
				saveWodBtn.setVisibility(View.VISIBLE);
				explnButton.setVisibility(View.GONE);
				wod_type.setGravity(Gravity.CENTER);
			}
		}); // end pause setOnClickListener

		// SAVE WOD BUTTON
		// ******************
		saveWodBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v3) {

				TinyDB tinydb = new TinyDB(v.getContext());

				if (timedWod || progPoint == 7) {
					String score = userScore.getText().toString();
					wodTimeString = score;
				} else
					wodTimeString = timerValue.getText().toString();

				if (isRandom == true) {
					boolean noLog = true;

					// get today's date
					Date today = Calendar.getInstance().getTime();
					// format date
					SimpleDateFormat formatter = new SimpleDateFormat(
							"hh:mma - MMMM dd yyyy");
					String wodDate = formatter.format(today);

					// get and crop screen shot
					Bitmap bmp = screenShot(v);
					int vWidth = v.getWidth();
					bmp = cropPic(bmp, progPoint, vWidth);

					// save screen shot and set path
					String path = saveToInternalStorage(v, bmp);

					// erase and replace old path with new path
					tinydb.remove("path");
					tinydb.putString("path", path);

					// GOES TO CONGRATULATIONS
					Intent toCongrats = new Intent(v.getContext(),
							CongratsAct.class);
					toCongrats.putExtra("wodTime", wodTimeString);
					toCongrats.putExtra("noLog", noLog);
					startActivity(toCongrats);

				} else {
					// this is where we keep up with what moves have been
					// used. we will use this to populate the Movement
					// Selection gallery for next work out
					ArrayList<String> usedMoves = new ArrayList<String>();
					int usedMovesCounter = tinydb.getInt("usedMovesCounter");

					if (usedMovesCounter != 0) {
						usedMoves = tinydb.getList("usedMoves");
						usedMovesCounter = usedMoves.size() - 1;
					} else
						usedMovesCounter = 0;

					String temp;
					for (int i = 0; i < mMovements.size(); i++) {
						temp = Integer.toString(mMovements.get(i));

						// remove flags if they have not been removed
						if (mMovements.get(i).equals(55)
								|| mMovements.get(i).equals(77)
								|| mMovements.get(i).equals(99)) {
							mMovements.remove(i);
						} else {
							usedMoves.add(usedMovesCounter, temp);
							usedMovesCounter++;
						}
					} // end for

					if (presetWod == false) {
						tinydb.putList("usedMoves", usedMoves);
						tinydb.putInt("usedMovesCounter", usedMovesCounter);
					}

					// get today's date
					Date today = Calendar.getInstance().getTime();
					// format date
					SimpleDateFormat formatter = new SimpleDateFormat(
							"hh:mma - MMMM dd yyyy");
					String wodDate = formatter.format(today);

					// get and crop screen shot
					Bitmap bmp = screenShot(v);
					int vWidth = v.getWidth();
					bmp = cropPic(bmp, progPoint, vWidth);

					// save screen shot and set path
					String path = saveToInternalStorage(v, bmp);

					// erase and replace old path with new path
					tinydb.remove("path");
					tinydb.putString("path", path);

					ArrayList<String> imageList = new ArrayList<String>();
					ArrayList<String> dateList = new ArrayList<String>();

					// save week and work out numbers for
					// progression
					int weekNum = tinydb.getInt("weekNum");
					int workoutNum = tinydb.getInt("workoutNum");

					if (workoutNum == 3) {
						weekNum++;
						workoutNum = 1;
					} else
						workoutNum++;

					tinydb.putInt("workoutNum", workoutNum);
					tinydb.putInt("weekNum", weekNum);

					// save list of paths to screen shots
					imageList = tinydb.getList("imageList");
					imageList.add(progPoint, path);
					tinydb.putList("imageList", imageList);

					// save list of dates of work outs
					dateList = tinydb.getList("dateList");
					dateList.add(progPoint, wodDate);
					tinydb.putList("dateList", dateList);

					// increment progression point (this keeps up with
					// how far the user is in the work out array)
					Integer progPoint = tinydb.getInt("progPoint");
					progPoint++;
					tinydb.putInt("progPoint", progPoint);
					Log.d("PATH", Integer.toString(progPoint));

					// increment and store timed WOD info
					if (skillDay == true) { // skillDay -> not scored
						skillDay = false;
						tinydb.putBoolean("skillDay", skillDay);
						// increment and save (timedWodCounter)
						incrTimedWodCounter();

					} // emom -> not scored
					else if (emom == true) {
						// increment and save (timedWodCounter)
						incrTimedWodCounter();
					} else if (progPoint == 3 || progPoint == 4
							|| progPoint == 10 || progPoint == 16
							|| progPoint == 21) {
						// increment and save (timedWodCounter)
						incrTimedWodCounter();
					}

					// if it's a preset WOD day, increment the cfCounter
					// when the user finishes their WOD
					if (presetWod == true) {
						cfCounter = tinydb.getInt("cfCounter");
						cfCounter++;
						tinydb.putInt("cfCounter", cfCounter);
						presetWod = false;
						tinydb.putBoolean("presetWod", presetWod);
					}

					saveWodBtn.setVisibility(View.GONE);

					new CountDownTimer(1000, decrementBy) {
						public void onTick(long millisUntilFinished) {
						}

						public void onFinish() {
							backToCongrats.setVisibility(View.VISIBLE);
						}
					}.start();

					// GOES TO CONGRATULATIONS
					Intent toCongrats = new Intent(v.getContext(),
							CongratsAct.class);
					toCongrats.putExtra("wodTime", wodTimeString);
					startActivity(toCongrats);
				}
			}
		}); // end saveWodBtn setOnClickListener

		// BACK TO CONGRATS BUTTON
		// **************************
		// used to navigate to Congrats after WOD results are saved
		backToCongrats.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// GOES TO CONGRATULATIONS
				Intent toCongrats = new Intent(v.getContext(),
						CongratsAct.class);
				toCongrats.putExtra("wodTime", wodTimeString);
				startActivity(toCongrats);
			}
		});

		// DELETE BUTTON
		// ****************
		delete_move.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// check to make sure mMovements isn't empty
				if (mMovements.size() > 0) {
					mMovements.remove(mMovements.size() - 1);

					// update the listView with the value deleted
					((MovementsAdapter) getListAdapter())
							.notifyDataSetChanged();

					// using set function from WODListActivity
					Activity activity = getActivity();
					if (activity instanceof WODListAct)
						((WODListAct) activity).setMovements(mMovements);

					finalize_wod.setVisibility(View.GONE);
					add_move.setVisibility(View.VISIBLE);
				}

				if (mMovements.size() == 0)
					delete_move.setVisibility(View.GONE);
			}
		}); // end of delete_move setOnClickListener

		if (hasUpgrade == true)
			setHasOptionsMenu(false);
		else
			setHasOptionsMenu(true);

		return v;
	} // end onCreateView

	public Bitmap cropPic(Bitmap bmp, Integer progPoint, Integer vWidth) {

		double ratio, tempHeight = 0.0;
		int vHeight = 0;
		// ToastShooter tShoot2 = new ToastShooter();
		// tShoot2.displayToast("Crop Height: " + vHeight, getActivity());

		Display display = getActivity().getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int height = size.y;

		// 1 MOVE // // SKILL DAYS //
		// ratio multiplied by screen height gives appropriate
		// crop figure for height for any device
		if (progPoint == 2 || progPoint == 5 || progPoint == 10
				|| progPoint == 14 || progPoint == 19) {
			ratio = 0.3625;
			tempHeight = ratio * height;
			vHeight = (int) tempHeight;
			bmp = Bitmap.createBitmap(bmp, 0, 0, vWidth, vHeight);
		}
		// DEATH BY //
		else if (progPoint == 7) {
			ratio = 0.41875;
			tempHeight = ratio * height;
			vHeight = (int) tempHeight;
			bmp = Bitmap.createBitmap(bmp, 0, 0, vWidth, vHeight);
		}
		// 2 MOVES // NICOLE //
		else if (progPoint == 1 || progPoint == 4 || progPoint == 6
				|| progPoint == 9 || progPoint == 13 || progPoint == 15
				|| progPoint == 16 || progPoint == 17 || progPoint == 22) {
			ratio = 0.4875;
			tempHeight = ratio * height;
			vHeight = (int) tempHeight;
			bmp = Bitmap.createBitmap(bmp, 0, 0, vWidth, vHeight);
		}
		// BACK TO BACK //
		else if (progPoint == 3) {
			ratio = 0.66875;
			tempHeight = ratio * height;
			vHeight = (int) tempHeight;
			bmp = Bitmap.createBitmap(bmp, 0, 0, vWidth, vHeight);
		}
		// ANGIE // LOREDO
		else if (progPoint == 12 || progPoint == 18) {
			ratio = 0.6875;
			tempHeight = ratio * height;
			vHeight = (int) tempHeight;
			bmp = Bitmap.createBitmap(bmp, 0, 0, vWidth, vHeight);
		}
		// 5 MOVES // THE FIFTY // MURPH //
		else if (progPoint == 11 || progPoint == 23) {
			ratio = 0.7875;
			tempHeight = ratio * height;
			vHeight = (int) tempHeight;
			bmp = Bitmap.createBitmap(bmp, 0, 0, vWidth, vHeight);
		}
		// 3 MOVES
		else {
			ratio = 0.5875;
			tempHeight = ratio * height;
			vHeight = (int) tempHeight;
			bmp = Bitmap.createBitmap(bmp, 0, 0, vWidth, vHeight);
		}

		return bmp;
	}

	// INCREMENTS TIMED WOD COUNTER
	// *******************************
	public void incrTimedWodCounter() {
		// ToastShooter tShoot2 = new ToastShooter();
		// tShoot2.displayToast("timedWodCounter++", getActivity());
		TinyDB tinydb = new TinyDB(getActivity().getBaseContext());
		timedWodCounter = tinydb.getInt("timedWodCounter");
		timedWodCounter++;
		tinydb.putInt("timedWodCounter", timedWodCounter);
	}

	// TIMER
	// ********
	// function that runs the timer after the count down is complete (counts up)
	public Runnable updateTimerThread = new Runnable() {
		public void run() {

			timerValue.setTextSize(82);
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			updatedTime = timeSwapBuff + timeInMilliseconds;

			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			secs = secs % 60;

			int milliseconds = (int) (updatedTime % 1000);
			// remove last 2 digits so we can display single digit only
			milliseconds = milliseconds / 100;

			if (mins > 9) {
				timerValue.setText("" + mins + ":"
						+ String.format("%02d", secs) + ":" + milliseconds);
				timerValue.setTextSize(72);
			} else
				timerValue.setText("" + mins + ":"
						+ String.format("%02d", secs) + ":" + milliseconds);

			customHandler.postDelayed(this, 0);

			// ***********************************************************//
			// this is the place to change the stopTime from secs to min
			// ***********************************************************//
			// if user enters score
			if (mins == stopTime && timedWod == true) { // ***secs is test value

				// stop clock
				timeSwapBuff += timeInMilliseconds;
				customHandler.removeCallbacks(updateTimerThread);

				// set milliseconds to avoid running over
				timerValue.setText("" + mins + ":"
						+ String.format("%02d", secs) + ":" + milliseconds);

				new CountDownTimer(countdownStart, decrementBy) {

					public void onTick(long millisUntilFinished) {
					}

					public void onFinish() {

						timerValue.setVisibility(View.GONE);

						if (skillDay == true) {
							saveWodBtn.setVisibility(View.VISIBLE);
							userScore.setText("Finished");
							userScore.setTextSize(72);
							userScore.setTypeface(null, Typeface.BOLD);
							userScore.setVisibility(View.VISIBLE);
							wod_type.setGravity(Gravity.CENTER);
							explnButton.setVisibility(View.GONE);
							wod_explanation.setVisibility(View.GONE);
						} else if (emom == true) { // no score
							saveWodBtn.setVisibility(View.VISIBLE);
							userScore.setText("Finished");
							userScore.setTextSize(72);
							userScore.setTypeface(null, Typeface.BOLD);
							userScore.setVisibility(View.VISIBLE);
							wod_type.setGravity(Gravity.CENTER);
							explnButton.setVisibility(View.GONE);
							wod_explanation.setVisibility(View.GONE);
						} else { // allow the user to enter their score
							// remove rest minute list item from "Back To Back"
							if (addListItem == true) {
								addListItem = false;
								mMovements.remove(4);
								MovementsAdapter adapter = new MovementsAdapter(
										mMovements);
								setListAdapter(adapter);
								adapter.setButtonsVisible(true);
							}

							enterScoreDialog(currentWod);
							wod_explanation.setVisibility(View.GONE);
						}
					}
				}.start();
			}
		}
	};

	// ALERT DIALOG FOR USER ENTERED SCORE
	// **************************************
	@SuppressWarnings("deprecation")
	public void enterScoreDialog(final String currentWod) {
		AlertDialog alert = new AlertDialog.Builder(getActivity()).create();

		int maxLength;
		// CONDITION FOR REPS OR ROUNDS
		// if rounds, user can enter 2 digit score, else if reps,
		// user can enter 3 digit score
		if (currentWod == "DEATH BY" || currentWod == "10 MIN AMRAP"
				|| currentWod == "CINDY") {
			maxLength = 2;
			alert.setTitle("Rounds Completed");
		} else {
			// number of reps is score
			maxLength = 3;
			alert.setTitle("Reps Completed");
		}

		// Set an EditText view to get user input
		final EditText input = new EditText(getActivity());
		// only allow digit input
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(maxLength);
		input.setFilters(FilterArray);
		alert.setView(input);
		alert.setCancelable(false);

		alert.setButton("Enter Score", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ToastShooter tShoot = new ToastShooter();
				String score = input.getText().toString();

				// validate that user has entered their score
				if (score.matches("") || score.matches("0")
						|| score.matches("00") || score.matches("000"))
					// no score entered
					tShoot.displayToast(R.drawable.skull_icon,
							"\tNo Score Entered", getActivity());
				else { // score is valid
						// remove leading zeroes if necessary
					if (score.length() > 1
							&& score.substring(0, 2).matches("00"))
						score = score.substring(2);
					else if (score.substring(0, 1).matches("0"))
						score = score.substring(1);

					enter_score.setTextSize(82);
					enter_score.setGravity(Gravity.CENTER);
					wod_type.setGravity(Gravity.CENTER);
					explnButton.setVisibility(View.GONE);

					scoreEntered = true;
				}

				// Score has been validated
				if (scoreEntered == true) {
					// force keyboard to disappear if still visible
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(enter_score.getWindowToken(), 0);

					// number of rounds is the score
					if (currentWod == "DEATH BY"
							|| currentWod == "10 MIN AMRAP"
							|| currentWod == "CINDY") {

						userScore.setText(score + " rounds");
						userScore.setTextSize(62);
					} else { // number of reps is the score
						userScore.setText(score + " reps");
						userScore.setTextSize(62);
					}

					timerValue.setVisibility(View.GONE);
					userScore.setVisibility(View.VISIBLE);
					userScore.setTypeface(null, Typeface.BOLD);
					saveWodBtn.setVisibility(View.VISIBLE);
					dialog.dismiss();
				} else
					enterScoreDialog(currentWod);
			}
		});
		alert.show();

		// center title of dialog
		TextView titleView = (TextView) alert.findViewById(getResources()
				.getIdentifier("alertTitle", "id", "android"));
		if (titleView != null)
			titleView.setGravity(Gravity.CENTER);
	}

	// SAVE SCREEN SHOT TO INTERNAL STORAGE AND RETURN PATH TO IMAGE
	// ****************************************************************
	private String saveToInternalStorage(View v, Bitmap bitmapImage) {

		ContextWrapper cw = new ContextWrapper(v.getContext());
		TinyDB tinydb = new TinyDB(v.getContext());
		String imageDir = weekWorkout[progPoint];

		// path to /data/data/yourapp/app_data/imageDir
		File directory = cw.getDir(imageDir, Context.MODE_PRIVATE);
		// Create imageDir
		File myfile = new File(directory, imageDir + ".jpg");

		if (isRandom != true) {
			ArrayList<String> imageAddressJpgList = new ArrayList<String>();
			imageAddressJpgList = tinydb.getList("imageAddressJpgList");
			imageAddressJpgList.add(progPoint, imageDir + ".jpg");
			tinydb.putList("imageAddressJpgList", imageAddressJpgList);
		}

		picFileName = imageDir + ".jpg";
		tinydb.remove("picFileName");
		tinydb.putString("picFileName", picFileName);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(myfile);

			// Use the compress method on the BitMap object to write image to
			// the OutputStream
			bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
			// //////////////////////////////////
			fos.flush();
			// //////////////////////////////////
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return directory.getAbsolutePath();
	} // end saveToInternalStorage()

	// TAKES SCREEN SHOT
	// ********************
	public Bitmap screenShot(View view) {
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);

		return bitmap;
	}

	// COUNTDOWN TIMER
	// ******************
	// does count down before the timer starts
	public void countdown() {
		new CountDownTimer(countdownStart, decrementBy) {
			// caveat-the last tick won't display with this
			// built in function
			public void onTick(long m) {
				long sec = m / 1000 + 1;
				countdownValue.setText(String.valueOf(sec - 1));

				if (sec < 5) // red from 3 down (1 is subtracted)
					countdownValue.setTextColor(getResources().getColor(
							R.color.red));
				else
					// green until 3
					countdownValue.setTextColor(getResources().getColor(
							R.color.green));

				countdownValue.setVisibility(View.VISIBLE);
			} // end onTick

			public void onFinish() {
				countdownValue.setVisibility(View.GONE);
				timerValue.setVisibility(View.VISIBLE);
				timerValue.setTextSize(82);
			}
		}.start();
	} // end countdown()

	// HANDLES LIST ITEM SET-UP
	// ***************************
	public class MovementsAdapter extends ArrayAdapter<Integer> {
		private ArrayList<Integer> test = new ArrayList<Integer>();

		public MovementsAdapter(ArrayList<Integer> movements) {
			super(getActivity(), 0, movements);
			test.addAll(movements);
		}

		private boolean buttonsVisible = false;

		// allows you to set the visibility of the buttons on the
		// list item dynamically
		public void setButtonsVisible(boolean isVisible) {
			buttonsVisible = isVisible;
		}

		@Override
		public View getView(final int movePosition, View convertView,
				ViewGroup parent) {

			if (convertView == null)
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.wod_list_item, null);

			TextView movementName = (TextView) convertView
					.findViewById(R.id.movement_list_item_name);
			TextView movementReps = (TextView) convertView
					.findViewById(R.id.movement_list_item_reps);
			final Button aboutMove = (Button) convertView
					.findViewById(R.id.btnViewAbout);

			aboutMove.setVisibility(buttonsVisible ? View.VISIBLE
					: View.INVISIBLE);

			Movement m = new Movement();
			CharSequence movement = getResources().getText(R.string.movement);
			CharSequence reps = getResources().getText(R.string.reps);
			CharSequence skill = getResources().getText(R.string.skill);
			CharSequence distance = getResources().getText(R.string.distance);

			// if SKILL DAY
			// if (test.size() == 1 && test.get(0) == 99) {
			if (test.get(0) == 99) {
				if (test.size() < 2) {
					movementName.setText("Work on the following :");

					// match appropriate skill with it's Skill Day
					if (m.wodStylesUpgraded[progPoint] == "SKILL DAY: 15 MIN") {
						movementReps.setText(TextUtils.concat(skill,
								m.skills[0]));
					} else if (m.wodStylesUpgraded[progPoint] == "SKILL DAY: 20 MIN") {
						movementReps.setText(TextUtils.concat(skill,
								m.skills[1]));
					} else if (m.wodStylesUpgraded[progPoint] == "SKILL DAY: 25 MIN") {
						movementReps.setText(TextUtils.concat(skill,
								m.skills[2]));
					} else if (m.wodStylesUpgraded[progPoint] == "SKILL DAY: 30 MIN") {
						movementReps.setText(TextUtils.concat(skill,
								m.skills[3]));
					}
				} // end if (test.size() < 2)
			} else { // is not SKILL DAY
				final int move = getItem(movePosition);

				// set movement names of list item
				if (isPreset == false) {
					if (move != 55)
						movementName.setText(TextUtils.concat(movement,
								m.movementNames[move]));
					else
						movementName.setText(TextUtils.concat(movement,
								"Catch your breath"));
				}

				// BUTTERCUP //
				if (doButtercup == true) {
					if (m.wodStylesUpgraded[progPoint] == "3 ROUNDS FOR TIME") {
						// have to dump reps from each movement on the list
						// and half them, then replace them for 3 Rounds WOD
						if (move == 6)
							movementReps.setText(TextUtils.concat(distance,
									"1/8 mile"));
						else {
							int fullRepsInt = Integer.valueOf(repsAdj[move]);
							int halfRepsInt = fullRepsInt / 2;
							String halfRepsStr = Integer.toString(halfRepsInt);
							movementReps.setText(TextUtils.concat(reps,
									halfRepsStr));
						}
					} else if (m.wodStylesUpgraded[progPoint] == "THE FIFTY") {
						// make all reps 25 (run = 1/8 mile)
						if (move == 6)
							movementReps.setText(TextUtils.concat(distance,
									"1/8 mile"));
						else
							movementReps.setText(TextUtils.concat(reps, "25"));
					} else if (m.wodStylesUpgraded[progPoint] == "ANGIE")
						// all reps 50
						movementReps.setText(TextUtils.concat(reps, "50"));
					else if (m.wodStylesUpgraded[progPoint] == "ANNIE")
						// 25,20,15,10,5
						movementReps.setText(TextUtils.concat(reps,
								"25-20-15-10-5"));
					else if (m.wodStylesUpgraded[progPoint] == "HERO WOD: LOREDO") {
						// all reps 12, run = 1/8 mile
						if (move == 6)
							movementReps.setText(TextUtils.concat(distance,
									"1/8 mile"));
						else
							movementReps.setText(TextUtils.concat(reps, "12"));
					} else if (m.wodStylesUpgraded[progPoint] == "HERO WOD: MURPH") {
						// runs = 1/2 mile, 50, 100, 150
						if (move == 6)
							movementReps.setText(TextUtils.concat(distance,
									"1/2 mile"));
						else if (move == 1)
							movementReps.setText(TextUtils.concat(reps, "50"));
						else if (move == 0)
							movementReps.setText(TextUtils.concat(reps, "100"));
						else if (move == 3)
							movementReps.setText(TextUtils.concat(reps, "150"));
					}
				}
				// NO BUTTERCUP //
				else { // 3 ROUNDS FOR TIME //
					if (m.wodStylesUpgraded[progPoint] == "3 ROUNDS FOR TIME"
							|| m.wodStylesUpgraded[progPoint] == "10 MIN AMRAP") {
						if (move == 6)
							movementReps.setText(TextUtils.concat(distance,
									"1/4 mile"));
						else
							// if the WOD uses the adjusted reps
							movementReps.setText(TextUtils.concat(reps,
									repsAdj[move]));
					} // EMOM // (half reps so list can be done in a min)
					else if (m.wodStylesUpgraded[progPoint] == "EMOM: 7 MIN"
							|| m.wodStylesUpgraded[progPoint] == "EMOM: 10 MIN") {
						// half reps
						int fullRepsInt = Integer.valueOf(repsAdj[move]);
						int halfRepsInt = fullRepsInt / 2;
						String halfRepsStr = Integer.toString(halfRepsInt);

						if (move == 6)
							movementReps
									.setText("\tSprint 10 yards each round");
						else
							movementReps.setText(TextUtils.concat(reps,
									halfRepsStr));
					} // RAPID DESCENT, DEATH BY //
					else if (m.wodStylesUpgraded[progPoint] == "RAPID DESCENT"
							|| m.wodStylesUpgraded[progPoint] == "DEATH BY") {
						if (move == 6)
							movementReps
									.setText("\tSprint 10 yards each round");
						else
							movementReps.setText(TextUtils.concat(reps,
									m.wodReps[progPoint]));
					} // set Run distance to 1/4 mile
					else if (m.wodStylesUpgraded[progPoint] == "THE FIFTY"
							|| m.wodStylesUpgraded[progPoint] == "NICOLE"
							|| m.wodStylesUpgraded[progPoint] == "21-15-9"
							|| m.wodStylesUpgraded[progPoint] == "HERO WOD: LOREDO") {
						if (move == 6)
							movementReps.setText(TextUtils.concat(distance,
									"1/4 mile"));
						else
							movementReps.setText(TextUtils.concat(reps,
									m.wodReps[progPoint]));
					} // set the rep scheme for Cindy
					else if (m.wodStylesUpgraded[progPoint] == "CINDY") {
						if (move == 1)
							movementReps.setText(TextUtils.concat(reps, "5"));
						else if (move == 0)
							movementReps.setText(TextUtils.concat(reps, "10"));
						else
							movementReps.setText(TextUtils.concat(reps, "15"));
					} // set the rep scheme for Murph
					else if (m.wodStylesUpgraded[progPoint] == "HERO WOD: MURPH") {
						if (move == 6)
							movementReps.setText(TextUtils.concat(distance,
									"1 mile"));
						else if (move == 1)
							movementReps.setText(TextUtils.concat(reps, "100"));
						else if (move == 0)
							movementReps.setText(TextUtils.concat(reps, "200"));
						else
							movementReps.setText(TextUtils.concat(reps, "300"));
					} // set reps if Run is chosen in Back to Back; also add
						// list item after WOD is confirmed for minute of rest
					else if (m.wodStylesUpgraded[progPoint] == "BACK TO BACK") {
						if (move == 6)
							movementReps.setText("\tRun for 1 minute");
						else if (move == 55) {
							movementReps.setText("\tRest for 1 minute");
							aboutMove.setVisibility(View.GONE);
						} else
							movementReps.setText(TextUtils.concat(reps,
									m.wodReps[progPoint]));
					} else
						movementReps.setText(TextUtils.concat(reps,
								m.wodReps[progPoint]));
				}
			}

			// ABOUT BUTTON
			// ***************
			aboutMove.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					final int move = getItem(movePosition);

					if (timerRunning == false) {
						// GOES TO DEMO VID
						Intent demoMov = new Intent(v.getContext(),
								DemoVidAct.class);
						demoMov.putExtra("move", move);
						demoMov.putExtra("progPoint", progPoint);
						startActivity(demoMov);
					}
				}
			});

			return convertView;
		} // end getView
	} // end MovementsAdapter
}
