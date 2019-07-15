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
import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class WODListAct extends SingleFragAct {

	ArrayList<Integer> movements = new ArrayList<Integer>();
	ArrayList<Integer> backArray = new ArrayList<Integer>();

	@Override
	protected Fragment createFragment() {
		movements.addAll((Collection<? extends Integer>) getIntent()
				.getSerializableExtra(WODListFrag.NEW_MOVE_ADDED));

		return WODListFrag.newInstance(movements);
	}

	@Override
	public void onPause() {
		super.onPause();
		setMovements(movements);
	}

	public void setMovements(ArrayList<Integer> moves) {
		Intent data = new Intent();
		data.putExtra(WODListFrag.NEW_MOVE_ADDED, moves);
		setResult(Activity.RESULT_OK, data);
	}

	@Override
	public void onBackPressed() {
		ToastShooter tShoot = new ToastShooter();
		TinyDB tinyDB = new TinyDB(this.getBaseContext());
		Intent i = getIntent();
		int moveCount = i.getExtras().getInt("moveCount");
		boolean timerRunning = tinyDB.getBoolean("timerRunning");
		int progPoint = tinyDB.getInt("progPoint");

		if (timerRunning)
			;
		else if (movements.size() == moveCount)
			tShoot.displayToast("Remove one", this);
		else if (progPoint == 3 && movements.size() == moveCount + 1)
			tShoot.displayToast("Workout set", this);
		else
			super.onBackPressed();
	}
}