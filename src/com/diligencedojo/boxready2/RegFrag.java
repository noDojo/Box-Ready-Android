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

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class RegFrag extends Fragment {
	public static final String TAG = "RegistrationFragment";

	private Spinner flSpinner;
	String[] repsChanged;
	Integer sex, age, fitnessLevel, spinnerChoice, savedFitnessLevel, savedAge,
			savedSex;
	boolean lowFlag, midFlag, highFlag, maleFlag, femaleFlag, hasWindowFocus,
			ageOk = false;
	boolean hasUpgrade = true;
	String saveAge, FILE_NAME;
	ArrayAdapter<CharSequence> adapter;
	Button lowAge, midAge, highAge, male, female, malePushed, femalePushed,
			maleLayered, femaleLayered, mFinalizeReg;
	TextView spinner, spinnerOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.reg_two, parent, false);

		// hide action bar
		getActivity().getActionBar().hide();

		// spinner construction
		flSpinner = (Spinner) v.findViewById(R.id.fitnessSpinner);
		adapter = ArrayAdapter.createFromResource(v.getContext(),
				R.array.fitness_level_spinner_array, R.layout.spinner_closed);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(R.layout.spinner_open);
		flSpinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter,
				R.layout.spinner_closed, v.getContext()));

		// only used in the listener below to keep the compiler from complaining
		final View v2 = v;

		// when an item is selected from the spinner, the stroke and the
		// text are changed to green to indicate the selection
		flSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {

				spinner = (TextView) v2.findViewById(R.id.spinner_closed);
				spinnerOverlay = (TextView) v2
						.findViewById(R.id.spinner_overlay);
				spinnerChoice = flSpinner.getSelectedItemPosition();

				if (spinnerChoice != 0) {
					spinner.setTextColor(getResources().getColor(R.color.green));
					spinner.setShadowLayer(2, 0, 0, Color.BLACK);
					spinnerOverlay.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}
		});

		mFinalizeReg = (Button) v.findViewById(R.id.finalize_reg_info);
		lowAge = (Button) v.findViewById(R.id.low_age_button);
		midAge = (Button) v.findViewById(R.id.mid_age_button);
		highAge = (Button) v.findViewById(R.id.high_age_button);
		male = (Button) v.findViewById(R.id.male_button);
		female = (Button) v.findViewById(R.id.female_button);
		malePushed = (Button) v.findViewById(R.id.male_button_pushed);
		femalePushed = (Button) v.findViewById(R.id.female_button_pushed);
		maleLayered = (Button) v.findViewById(R.id.male_layer_button);
		femaleLayered = (Button) v.findViewById(R.id.female_layer_button);

		TinyDB tinydb = new TinyDB(v.getContext());

		// retrieve and set sex
		savedSex = tinydb.getInt("savedSex");
		if (savedSex != 0) {
			sex = savedSex;

			if (sex == 1) {
				male.setBackgroundResource(R.drawable.male_button_green);
				malePushed.setVisibility(View.VISIBLE);
				maleLayered.setVisibility(View.INVISIBLE);
				maleFlag = true;
			} else if (sex == 2) {
				female.setBackgroundResource(R.drawable.female_button_green);
				femalePushed.setVisibility(View.VISIBLE);
				femaleLayered.setVisibility(View.INVISIBLE);
				femaleFlag = true;
			}
		} else
			sex = 3; // 3 is a placeholder value
		// 1 = male, 2 = female, 0 = nothing saved yet
		// end saved sex

		// retrieve and set age
		savedAge = tinydb.getInt("savedAge");
		if (savedAge != 0) {
			age = savedAge;
			ageOk = true;

			if (age == 0) {
				lowAge.setBackgroundResource(R.drawable.silver_green_button);
				lowAge.setTextColor(getResources().getColor(R.color.green));
				lowAge.setShadowLayer(2, 0, 0, Color.BLACK);
				lowFlag = true;
			} else if (age == 1) {
				midAge.setBackgroundResource(R.drawable.silver_green_button);
				midAge.setTextColor(getResources().getColor(R.color.green));
				midAge.setShadowLayer(2, 0, 0, Color.BLACK);
				midFlag = true;
			} else if (age == 2) {
				highAge.setBackgroundResource(R.drawable.silver_green_button);
				highAge.setTextColor(getResources().getColor(R.color.green));
				highAge.setShadowLayer(2, 0, 0, Color.BLACK);
				highFlag = true;
			}
		} // end saved age

		// retrieve and set fitness level
		savedFitnessLevel = tinydb.getInt("savedFitnessLevel");
		if (savedFitnessLevel != 0)
			flSpinner.setSelection(savedFitnessLevel);

		// GENDER BUTTONS
		// *****************
		// MALE BUTTON *****
		male.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sex = 1;

				// if the female button has been clicked, this
				// removes the highlight from it so that the male button can
				// be highlighted
				if (femaleFlag == true) {
					female.setBackgroundResource(R.drawable.female_button);
					femalePushed.setVisibility(View.INVISIBLE);
					femaleLayered.setVisibility(View.VISIBLE);
					femaleFlag = false;
				}

				if (maleFlag == true) {
					male.setBackgroundResource(R.drawable.male_button);
					malePushed.setVisibility(View.INVISIBLE);
					maleLayered.setVisibility(View.VISIBLE);
					maleFlag = false;
				} else {
					male.setBackgroundResource(R.drawable.male_button_green);
					malePushed.setVisibility(View.VISIBLE);
					maleLayered.setVisibility(View.INVISIBLE);
					maleFlag = true;
				}
			}
		});
		// FEMALE BUTTON ***
		female.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sex = 2;

				// if the male button has been clicked, this
				// removes the highlight from it so that the female button can
				// be highlighted
				if (maleFlag == true) {
					male.setBackgroundResource(R.drawable.male_button);
					malePushed.setVisibility(View.INVISIBLE);
					maleLayered.setVisibility(View.VISIBLE);
					maleFlag = false;
				}

				if (femaleFlag == true) {
					female.setBackgroundResource(R.drawable.female_button);
					femalePushed.setVisibility(View.INVISIBLE);
					femaleLayered.setVisibility(View.VISIBLE);
					femaleFlag = false;
				} else {
					female.setBackgroundResource(R.drawable.female_button_green);
					femalePushed.setVisibility(View.VISIBLE);
					femaleLayered.setVisibility(View.INVISIBLE);
					femaleFlag = true;
				}
			}
		});

		// AGE BUTTONS
		// **************
		// LOW AGE BUTTON (Under 35)
		lowAge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ageOk = true;
				age = 0;

				// if one of the other age buttons has been clicked, this
				// removes the highlight from it so that lowAge can be
				// highlighted
				if (midFlag == true) {
					midAge.setBackgroundResource(R.drawable.silver_button_background);
					midAge.setTextColor(getResources().getColor(
							R.color.dark_gray));
					midAge.setShadowLayer(2, 0, 0, Color.WHITE);
					midFlag = false;
				} else if (highFlag == true) {
					highAge.setBackgroundResource(R.drawable.silver_button_background);
					highAge.setTextColor(getResources().getColor(
							R.color.dark_gray));
					highAge.setShadowLayer(2, 0, 0, Color.WHITE);
					highFlag = false;
				}

				// on odd clicks, highlight the lowAge button; on even clicks,
				// remove the highlight
				if (lowFlag == true) { // even
					lowAge.setBackgroundResource(R.drawable.silver_button_background);
					lowAge.setTextColor(getResources().getColor(
							R.color.dark_gray));
					lowAge.setShadowLayer(2, 0, 0, Color.WHITE);
					lowFlag = false;
				} else { // odd
					lowAge.setBackgroundResource(R.drawable.silver_green_button);
					lowAge.setTextColor(getResources().getColor(R.color.green));
					lowAge.setShadowLayer(2, 0, 0, Color.BLACK);
					lowFlag = true;
				}
			}
		});
		// MID AGE BUTTON (35 - 55)
		midAge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ageOk = true;
				age = 1;

				// if one of the other age buttons has been clicked, this
				// removes the highlight from it so that midAge can be
				// highlighted
				if (lowFlag == true) { // even
					lowAge.setBackgroundResource(R.drawable.silver_button_background);
					lowAge.setTextColor(getResources().getColor(
							R.color.dark_gray));
					lowAge.setShadowLayer(2, 0, 0, Color.WHITE);
					lowFlag = false;
				} else if (highFlag == true) { // odd
					highAge.setBackgroundResource(R.drawable.silver_button_background);
					highAge.setTextColor(getResources().getColor(
							R.color.dark_gray));
					highAge.setShadowLayer(2, 0, 0, Color.WHITE);
					highFlag = false;
				}

				// on odd clicks, highlight the midAge button; on even clicks,
				// remove the highlight
				if (midFlag == true) {
					midAge.setBackgroundResource(R.drawable.silver_button_background);
					midAge.setTextColor(getResources().getColor(
							R.color.dark_gray));
					midAge.setShadowLayer(2, 0, 0, Color.WHITE);
					midFlag = false;
				} else {
					midAge.setBackgroundResource(R.drawable.silver_green_button);
					midAge.setTextColor(getResources().getColor(R.color.green));
					midAge.setShadowLayer(2, 0, 0, Color.BLACK);
					midFlag = true;
				}
			}
		});
		// HIGH AGE BUTTON (Over 55)
		highAge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ageOk = true;
				age = 2;

				// if one of the other age buttons has been clicked, this
				// removes the highlight from it so that highAge can be
				// highlighted
				if (lowFlag == true) {
					lowAge.setBackgroundResource(R.drawable.silver_button_background);
					lowAge.setTextColor(getResources().getColor(
							R.color.dark_gray));
					lowAge.setShadowLayer(2, 0, 0, Color.WHITE);
					lowFlag = false;
				} else if (midFlag == true) {
					midAge.setBackgroundResource(R.drawable.silver_button_background);
					midAge.setTextColor(getResources().getColor(
							R.color.dark_gray));
					midAge.setShadowLayer(2, 0, 0, Color.WHITE);
					midFlag = false;
				}

				// on odd clicks, highlight the highAge button; on even clicks,
				// remove the highlight
				if (highFlag == true) { // even
					highAge.setBackgroundResource(R.drawable.silver_button_background);
					highAge.setTextColor(getResources().getColor(
							R.color.dark_gray));
					highAge.setShadowLayer(2, 0, 0, Color.WHITE);
					highFlag = false;
				} else { // odd
					highAge.setBackgroundResource(R.drawable.silver_green_button);
					highAge.setTextColor(getResources().getColor(R.color.green));
					highAge.setShadowLayer(2, 0, 0, Color.BLACK);
					highFlag = true;
				}
			}
		});

		// FINALIZE REGISTRATION BUTTON
		// *******************************
		mFinalizeReg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean regValidated = false;
				TinyDB tinydb = new TinyDB(v.getContext());
				ToastShooter tShoot = new ToastShooter();

				// SEX VALIDATION
				// *****************
				if (sex == 1 || sex == 2) // male || female
					regValidated = true;
				else { // no selection
					tShoot.displayToast("Choose Sex", getActivity());
					regValidated = false;
				}

				// AGE VALIDATION
				// *****************
				// if sex is validated, but age is not
				if (ageOk == false) {
					if (regValidated == true)
						tShoot.displayToast("Enter Age", getActivity());

					regValidated = false;
				} else
					regValidated = true;

				// FITNESS LEVEL VALIDATION
				// ***************************
				// get selection from fitness level spinner unless it is the
				// first element of the array (that is the -select one- display)
				spinnerChoice = flSpinner.getSelectedItemPosition();

				if (regValidated == true) {
					if (spinnerChoice == 0) {
						tShoot.displayToast("Select Fitness Level",
								getActivity());
						regValidated = false;
					} else {
						fitnessLevel = spinnerChoice;
						regValidated = true;
					}
				}

				// INPUT VALIDATION
				// *******************
				// When all inputs have been validated, adjust the REP scheme
				// based on the user's entries
				if (regValidated == true) {
					// save sex
					savedSex = tinydb.getInt("savedSex");
					savedSex = sex;
					tinydb.putInt("savedSex", savedSex);
					// save age
					savedAge = tinydb.getInt("savedAge");
					savedAge = age;
					tinydb.putInt("savedAge", savedAge);
					// save fitness level
					savedFitnessLevel = tinydb.getInt("savedFitnessLevel");
					savedFitnessLevel = fitnessLevel;
					tinydb.putInt("savedFitnessLevel", savedFitnessLevel);
					// indicates that registration info has been saved
					tinydb.putInt("regSaved", 1);
					// create RepAdjustment object to...guess what? adjust reps
					RepAdjustment ra = new RepAdjustment();
					repsChanged = ra.regConditions(sex, age, fitnessLevel);

					// convert from String[] (repsChanged) to ArrayList<String>
					// (savedRepArray) to use TinyDB class
					ArrayList<String> savedRepArray = new ArrayList<String>();
					for (int i = 0; i < repsChanged.length; i++)
						savedRepArray.add(i, repsChanged[i]);

					tinydb.putList("savedRepArray", savedRepArray);
					tinydb.putBoolean("regComplete", true);

					Intent regIntent = new Intent(v.getContext(), ExplAct.class);
					regIntent.putExtra("repsAdj", repsChanged);
					onIntentChange(regIntent);
				}
			} // end Finalize Registration onClick()
		}); // end Finalize Registration onClickListener()

		return v;
	}

	// START NEXT ACTIVITY
	// **********************
	public void onIntentChange(Intent regIntent) {
		startActivityForResult(regIntent, 0);
	}
}
