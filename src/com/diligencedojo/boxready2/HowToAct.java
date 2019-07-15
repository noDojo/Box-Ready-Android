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

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HowToAct extends Activity {

	Button dismiss;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.how_to_use);

		// hide action bar
		getActionBar().hide();

//		//Determine screen size
//		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
//		    Toast.makeText(this, "Large screen", Toast.LENGTH_LONG).show();
//		}
//		else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
//		    Toast.makeText(this, "Normal sized screen", Toast.LENGTH_LONG).show();
//		}
//		else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
//		    Toast.makeText(this, "Small sized screen", Toast.LENGTH_LONG).show();
//		}
//		else {
//		    Toast.makeText(this, "Screen size is neither large, normal or small", Toast.LENGTH_LONG).show();
//		}
//
//		//Determine density
//		DisplayMetrics metrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		int density = metrics.densityDpi;
//
//		if (density == DisplayMetrics.DENSITY_HIGH) {
//		    Toast.makeText(this, "DENSITY_HIGH... Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
//		}
//		else if (density == DisplayMetrics.DENSITY_MEDIUM) {
//		    Toast.makeText(this, "DENSITY_MEDIUM... Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
//		}
//		else if (density == DisplayMetrics.DENSITY_LOW) {
//		    Toast.makeText(this, "DENSITY_LOW... Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
//		}
//		else {
//		    Toast.makeText(this, "Density is neither HIGH, MEDIUM OR LOW.  Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
//		}

		dismiss = (Button) findViewById(R.id.dismiss_how_to);

		// dismiss how to screen and continue to Registration
		dismiss.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent toReg = new Intent(v.getContext(), RegAct.class);
				startActivity(toReg);
			}
		});

	}

}
