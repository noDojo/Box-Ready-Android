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

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diligencedojo.boxready2.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.plattysoft.leonids.ParticleSystem;

public class CongratsFrag extends Fragment {
	private final static String TAG = "com.diligencedojo.boxready";
	String upgradeToUse = "Upgrade to use this feature!";
	String noLogStr = "Random workouts are not logged.";
	String wodTime, badLogin, emailBodyStr, picFileName, path;
	boolean readyToPost, noLog, mailLayoutOpen, emailReady, emailSentOk,
			emailMsg = false;
	boolean hasUpgrade = true;
	int fireBtnFlag, fireFlag = 0;
	Button socialBtn, twShareBtn, logWod, contactUs, sendBtn, fireBtn1,
			fireBtn2, a1, a4, a5, a8;
	EditText emailBodyEt;
	TextView finalScore, done;
	Drawable pinkStar, whiteStar, redStar, greenStar, goldStar, ltGoldStar;
	String[] displayBtns = { "a1", "a2", "a3", "a4" };
	File f;
	Twitter twitt;

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.congrats, parent, false);

//		// look up the AdView as a resource and load a request
//		AdView adView = (AdView) v.findViewById(R.id.congratsAdView);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		try {
//			adView.loadAd(adRequest);
//		} catch (NoClassDefFoundError ex) {
//		}

		Intent i = getActivity().getIntent();
		wodTime = i.getExtras().getString("wodTime");
		noLog = i.getExtras().getBoolean("noLog");

		TinyDB tinydb = new TinyDB(v.getContext());
		// hasUpgrade = tinydb.getBoolean("hasUpgrade");
		path = tinydb.getString("path");
		picFileName = tinydb.getString("picFileName");
		f = new File(path, picFileName);

		// hide title text in action bar
		getActivity().getActionBar().hide();

		done = (TextView) v.findViewById(R.id.congrats);
		finalScore = (TextView) v.findViewById(R.id.timerValue);
		finalScore.setText(wodTime);
		socialBtn = (Button) v.findViewById(R.id.social_button);
		twShareBtn = (Button) v.findViewById(R.id.share_tweet_button);
		badLogin = v.getResources().getString(R.string.bad_login);
		logWod = (Button) v.findViewById(R.id.log_workout);
		contactUs = (Button) v.findViewById(R.id.contact_us_button);
		fireBtn1 = (Button) v.findViewById(R.id.fireworks_one);
		fireBtn2 = (Button) v.findViewById(R.id.fireworks_two);
		pinkStar = (Drawable) getActivity().getResources().getDrawable(
				R.drawable.star_white);
		whiteStar = (Drawable) getActivity().getResources().getDrawable(
				R.drawable.star_pink);
		redStar = (Drawable) getActivity().getResources().getDrawable(
				R.drawable.star_red);
		greenStar = (Drawable) getActivity().getResources().getDrawable(
				R.drawable.star_green);

		// lay invisible grid layout over original layout to
		// allow for locating fire work origins
		LinearLayout linLay = (LinearLayout) inflater.inflate(
				R.layout.fireworks, null);
		((ViewGroup) v).addView(linLay);
		// Row 1
		final Button a1 = (Button) v.findViewById(R.id.button1);
		final Button a4 = (Button) v.findViewById(R.id.button4);
		// Row 2
		final Button a5 = (Button) v.findViewById(R.id.button5);
		final Button a8 = (Button) v.findViewById(R.id.button8);

		// FIREWORKS BUTTON 1
		// *********************
		fireBtn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View fv1) {
				// skip first iteration, otherwise first explosion is
				// centered at the top left corner
				if (fireFlag == 0)
					fireFlag++;
				else if (fireFlag % 2 == 0) { // 2nd
					fireFlag++;
					new ParticleSystem(getActivity(), 100, whiteStar, 5000)
							.setSpeedRange(0.2f, 0.4f).oneShot(a5, 100);
					new ParticleSystem(getActivity(), 100, redStar, 5000)
							.setSpeedRange(0.2f, 0.5f).oneShot(a5, 100);
					new ParticleSystem(getActivity(), 100, pinkStar, 5000)
							.setSpeedRange(0.2f, 0.4f).oneShot(a8, 100);
					new ParticleSystem(getActivity(), 100, pinkStar, 5000)
							.setSpeedRange(0.2f, 0.4f).oneShot(a8, 100);
					new ParticleSystem(getActivity(), 100, redStar, 5000)
							.setSpeedRange(0.2f, 0.5f).oneShot(a8, 100);
					new ParticleSystem(getActivity(), 100, whiteStar, 5000)
							.setSpeedRange(0.2f, 0.4f).oneShot(a8, 100);
				} else { // 1st
					fireFlag++;
					new ParticleSystem(getActivity(), 500, whiteStar, 20000)
							.setSpeedRange(0.2f, 0.5f).oneShot(done, 100);
					new ParticleSystem(getActivity(), 500, whiteStar, 20000)
							.setSpeedRange(0.2f, 0.5f).oneShot(done, 100);
					new ParticleSystem(getActivity(), 500, pinkStar, 20000)
							.setSpeedRange(0.2f, 0.5f).oneShot(done, 100);
				}
			}
		});

		// FIREWORKS BUTTON 2
		// *********************
		fireBtn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// skip first iteration, otherwise first explosion is
				// centered at the top left corner
				if (fireFlag == 0)
					fireFlag++;
				else { // 1st
					fireFlag++;
					new ParticleSystem(getActivity(), 100, whiteStar, 5000)
							.setSpeedRange(0.2f, 0.4f).oneShot(a1, 100);
					new ParticleSystem(getActivity(), 100, pinkStar, 5000)
							.setSpeedRange(0.2f, 0.4f).oneShot(a4, 100);
					new ParticleSystem(getActivity(), 100, pinkStar, 5000)
							.setSpeedRange(0.2f, 0.4f).oneShot(a1, 100);
					new ParticleSystem(getActivity(), 100, whiteStar, 5000)
							.setSpeedRange(0.2f, 0.4f).oneShot(a4, 100);
					new ParticleSystem(getActivity(), 100, greenStar, 5000)
							.setSpeedRange(0.2f, 0.3f).oneShot(done, 100);

					// set all icons to visible after 2 seconds have passed
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						public void run() {
							new ParticleSystem(getActivity(), 500, whiteStar,
									20000).setSpeedRange(0.2f, 0.5f).oneShot(
									done, 100);
							new ParticleSystem(getActivity(), 500, whiteStar,
									20000).setSpeedRange(0.2f, 0.5f).oneShot(
									done, 100);
							new ParticleSystem(getActivity(), 500, pinkStar,
									20000).setSpeedRange(0.2f, 0.5f).oneShot(
									done, 100);
						}
					}, 1000);
				} // end else
			}
		});

		twitt = new Twitter(v.getContext()); // create twitter object
		// SOCIAL BUTTON
		// ****************
		socialBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				twitt.authorize();

				// after 3 seconds, change the button
				new CountDownTimer(2000, 1000) {
					public void onTick(long millisUntilFinished) {
					}

					public void onFinish() {
						// log in successful
						if (twitt.isAuthorized()) {
							socialBtn.setVisibility(View.GONE);
							twShareBtn.setVisibility(View.VISIBLE);
						} // log in failed
						else {
							twShareBtn.setVisibility(View.GONE);
							socialBtn.setVisibility(View.VISIBLE);
							ToastShooter tShoot = new ToastShooter();
							tShoot.displayToast(badLogin, getActivity());
						}
					}
				}.start();
			}
		});

		// LOG WOD BUTTON
		// *****************
		logWod.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastShooter tShoot = new ToastShooter();

				if (hasUpgrade == true) {
					// if upgraded, user gains access to work out log
					if (noLog == true)
						tShoot.displayToast(noLogStr, getActivity());

					Intent toLog = new Intent(v.getContext(), LogList.class);
					startActivity(toLog);
				} else
					// no upgrade -> user cannot view the work out log
					tShoot.displayToast(upgradeToUse, getActivity());
			}
		});

		// initiate email
		contactUs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toMail = new Intent(v.getContext(), MailAct.class);
				startActivity(toMail);
			}
		});

		// shoot off fireworks
		new CountDownTimer(8000, 2000) {
			public void onTick(long millisUntilFinished) {
				fireBtn1.performClick();
			}

			public void onFinish() {
				fireBtn2.performClick();
			}
		}.start();

		return v;
	}
}