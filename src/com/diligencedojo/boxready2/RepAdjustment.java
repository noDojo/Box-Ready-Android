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

import android.util.Log;

public class RepAdjustment {

	String[] repsChanged;

	// ****************************************************************************************************//
	// this function will adjust the user's rep scheme as they move through the
	// 8 week progression.
	// the savedRepArray is retrieved, adjusted, and saved again within this
	// function.
	// ****************************************************************************************************//
	public void upProgress() {

		Log.d("Progress: ", "turned up!"); // placeholder
	}

	// ****************************************************************************************************//
	// this function handles the registration condition settings
	// ****************************************************************************************************//
	public String[] regConditions(Integer sex, Integer age, Integer fitnessLevel) {

		// for access to the base-value rep array
		Movement m = new Movement();
		String[] modArray = new String[m.reps.length];
		Double adjustment;

		for (int i = 0; i < modArray.length; i++) {

			modArray[i] = m.reps[i];
		}

		// check for male**********
		// *************************//
		if (sex == 1) { // 1 = male?
			// 100% of base-value rep array

			// conditions for age of male
			// **************************//
			// if (age < 35) {
			// repsChanged = modArray;
			// } else if (age > 34 && age < 45) {
			// adjustment = 0.9;
			// repsChanged = adjustReps(adjustment, modArray);
			// for (int i = 0; i < repsChanged.length; i++) {
			// // Log.d("35-44", "new in: " + repsChanged[i]);
			// }
			// } else if (age > 44 && age < 55) {
			// adjustment = 0.8;
			// repsChanged = adjustReps(adjustment, modArray);
			// for (int i = 0; i < repsChanged.length; i++) {
			// // Log.d("45-54", "new in: " + repsChanged[i]);
			// }
			// } else { // age == 55+
			// adjustment = 0.75;
			// repsChanged = adjustReps(adjustment, modArray);
			// for (int i = 0; i < repsChanged.length; i++) {
			// // Log.d("55+", "new in: " + repsChanged[i]);
			// }
			// }

			if (age == 0) {
				repsChanged = modArray;
			} else if (age == 1) {
				adjustment = 0.85;
				repsChanged = adjustReps(adjustment, modArray);
			} else { // age == 55+
				adjustment = 0.75;
				repsChanged = adjustReps(adjustment, modArray);
			}

			// conditions for fitness level of male
			// ************************************//
			if (fitnessLevel == 1) { // out of shape
				adjustment = 0.9;
				repsChanged = adjustReps(adjustment, repsChanged);
				for (int i = 0; i < repsChanged.length; i++) {
					// Log.d("out of shape", "new in: " + repsChanged[i]);
				}
			} else if (fitnessLevel == 2) { // avg shape
				for (int i = 0; i < repsChanged.length; i++) {
					// 100% of reps
					// Log.d("out of shape", "new in: " + repsChanged[i]);
				}
			} else {// it can only equal 3 //
				adjustment = 1.1;
				repsChanged = adjustReps(adjustment, modArray);
				for (int i = 0; i < repsChanged.length; i++) {
					// Log.d("out of shape", "new in: " + repsChanged[i]);
				}
			}
		}

		// check for female**********
		// *************************//
		if (sex == 2) { // 0 = female?
			// 60% of base-value rep array
			adjustment = 0.6;
			repsChanged = adjustReps(adjustment, modArray);

			// conditions for age of female
			// **************************//
			// if (age < 35) {
			// for (int i = 0; i < repsChanged.length; i++) {
			// // Log.d("female", "new in: " + repsChanged[i]);
			// }
			// } else if (age > 34 && age < 45) {
			// adjustment = 0.9;
			// repsChanged = adjustReps(adjustment, modArray);
			// for (int i = 0; i < repsChanged.length; i++) {
			// // Log.d("f35-44", "new in: " + repsChanged[i]);
			// }
			// } else if (age > 44 && age < 55) {
			// adjustment = 0.8;
			// repsChanged = adjustReps(adjustment, modArray);
			// for (int i = 0; i < repsChanged.length; i++) {
			// // Log.d("f45-54", "new in: " + repsChanged[i]);
			// }
			// } else {// age == 55+
			// adjustment = 0.75;
			// repsChanged = adjustReps(adjustment, modArray);
			// for (int i = 0; i < repsChanged.length; i++) {
			// // Log.d("f55+", "new in: " + repsChanged[i]);
			// }
			// }

			if (age == 0) {
				repsChanged = modArray;
			} else if (age == 1) {
				adjustment = 0.85;
				repsChanged = adjustReps(adjustment, modArray);
			} else { // age == 55+
				adjustment = 0.75;
				repsChanged = adjustReps(adjustment, modArray);
			}

			// conditions for fitness level of female
			// ************************************//
			if (fitnessLevel == 1) { // out of shape?
				adjustment = 0.9;
				repsChanged = adjustReps(adjustment, repsChanged);
				for (int i = 0; i < repsChanged.length; i++) {
					// Log.d("fout of shape", "new in: " + repsChanged[i]);
				}
			} else if (fitnessLevel == 2) { // avg shape?
				for (int i = 0; i < repsChanged.length; i++) {
					// 100% of reps
					// Log.d("fout of shape", "new in: " + repsChanged[i]);
				}
			} else {// it can only equal 3 //
				adjustment = 1.1;
				repsChanged = adjustReps(adjustment, modArray);
				for (int i = 0; i < repsChanged.length; i++) {
					// Log.d("fout of shape", "new in: " + repsChanged[i]);
				}
			}

		}

		return repsChanged;
	}

	// ****************************************************************************************************//

	public String[] adjustReps(Double adjustment, String[] modArray) {

		Double[] dubModArray = new Double[modArray.length];
		Integer[] intArray = new Integer[modArray.length];

		// flag the run and change all values to doubles
		for (int i = 0; i < modArray.length; i++) {

			if (i == 6) {
				modArray[i] = "9999"; // flag run element
				dubModArray[i] = Double.valueOf(modArray[i]);
			} else {
				dubModArray[i] = Double.valueOf(modArray[i]);
			}
		}

		// replace flag and fill modArray w modified reps
		for (int i = 0; i < modArray.length; i++) {

			if (i == 6) {
				modArray[i] = "1/4 mile";
			} else {
				// adjust reps
				dubModArray[i] = dubModArray[i] * adjustment;
				// round values and put in int array
				intArray[i] = (int) Math.round(dubModArray[i]);
				// change to string
				modArray[i] = Integer.toString(intArray[i]);
			}
		}

		return modArray;
	}
}
