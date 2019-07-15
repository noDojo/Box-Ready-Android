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

import com.diligencedojo.boxready2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Splash extends Activity {

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.splash_screen,
				(ViewGroup) findViewById(R.id.splash));

		final Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 1, 1);
		toast.setView(layout);
		toast.show();

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				TinyDB tinydb = new TinyDB(getApplicationContext());
				Boolean regComplete = tinydb.getBoolean("regComplete");

				if (regComplete) {
					Splash.this.startActivity(new Intent(Splash.this,
							RegAct.class));
					Splash.this.finish();
				} else {
					Splash.this.startActivity(new Intent(Splash.this,
							HowToAct.class));
					Splash.this.finish();
				}
			}
		});
	}
}