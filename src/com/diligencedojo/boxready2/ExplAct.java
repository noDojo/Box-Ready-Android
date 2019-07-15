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

import android.support.v4.app.Fragment;

public class ExplAct extends SingleFragAct {

	@Override
	protected Fragment createFragment() {

		return new ExplFrag();
	}

	// LOCK BACK BUTTON
	// *******************
	// keeps user from navigating back to the Registration unless saved
	// data is erased (otherwise it will mess everything up)
	// @Override
	// public void onBackPressed() {
	//
	// Toast.makeText(getApplicationContext(), "Back button locked.",
	// Toast.LENGTH_SHORT).show();
	// Toast.makeText(
	// getApplicationContext(),
	// "To change profile information, press the \"View Log\" button.",
	// Toast.LENGTH_LONG).show();
	// Toast.makeText(getApplicationContext(),
	// "At Workout Log screen, select \"Clear Log\".", Toast.LENGTH_LONG)
	// .show();
	// }
}