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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MailAct extends Activity {
	// email address that messages will go to
	String emailAddress = "some_email@gmail.com";

	boolean mailLayoutOpen, emailReady, emailSentOk, emailMsg, checkTB = false;
	boolean firstTimeOpened = true;
	Button contactUsBtn, sendBtn;
	EditText emailBodyEt;
	String emailBodyStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mail_layout);
		setTitle(R.string.mail_action_title);

		emailBodyEt = (EditText) findViewById(R.id.editMailBody);
		sendBtn = (Button) findViewById(R.id.sendMailButton);

		// the next two lines control how text can be entered into
		// the email body by the user. they are necessary because
		// controlling horizontal scrolling from xml by using
		// textMuliLine won't let you use the done button on
		// the soft keyboard
		emailBodyEt.setHorizontallyScrolling(false);
		emailBodyEt.setMaxLines(Integer.MAX_VALUE);

		mailLayoutOpen = true;

		// every time the text is changed in emailBodyEt, check
		// if it is empty and if it is put the hint back in there
		emailBodyEt.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				emailBodyStr = emailBodyEt.getText().toString();

				if (emailBodyStr.isEmpty())
					emailBodyEt.setHint(R.string.mail_hint);
			}
		});

		// when emailBodyEt is clicked, erase the hint
		emailBodyEt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				emailBodyEt.setHint("");
			}
		});

		// get text from emailBodyEt and begin the sequence to
		// put it in an email
		sendBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				emailBodyStr = emailBodyEt.getText().toString();

				// if user is trying to send an empty email
				if (emailBodyStr.equals("Type email here...")
						|| emailBodyStr.isEmpty()) {
					ToastShooter tShoot = new ToastShooter();
					tShoot.displayToast("Cannot send empty email.",
							MailAct.this);
				} else {
					// prompt user to make sure their email is ready to
					// send by displaying an alert dialog so they have
					// to click it to get rid of it
					String title = "Email Ready";
					CharSequence msg = "Are you sure your email is ready to send?";
					CharSequence posTxt = "Yes, send it";
					CharSequence negTxt = "No";
					alertDlog(title, msg, posTxt, negTxt);
				}
			}
		}); // end sendBtn.setOnClickListener()

	} // end onCreate()

	// DISPLAY ALERT DIALOG
	// ***********************
	// accepts 4 parameters and displays an alert dialog box with either
	// both positive and negative buttons or a positive button only
	public void alertDlog(String title, CharSequence msg, CharSequence posTxt,
			CharSequence negTxt) {

		// set title to TextView so we can alter the title style
		TextView titleView = new TextView(getApplicationContext());
		titleView.setText(title);
		titleView.setGravity(Gravity.CENTER);
		titleView.setBackgroundColor(getResources().getColor(
				R.color.xtra_dark_blue));
		titleView.setPadding(10, 10, 10, 10);
		titleView.setTextColor(getResources().getColor(R.color.white));
		titleView.setTextSize(30);

		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setCustomTitle(titleView);
		ad.setMessage(msg);
		ad.setCancelable(false);

		// Positive button listener for dialog
		ad.setPositiveButton(posTxt, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				if (mailLayoutOpen) {
					emailReady = true;
					sendMail(emailBodyStr);
				}
				dialog.dismiss();
			}
		});

		// Negative button listener for dialog
		ad.setNegativeButton(negTxt, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		ad.show();
	} // end alertDlog()

	// SEND EMAIL TO IKS
	// ********************
	public void sendMail(String emailBodyStr) {

		if (emailReady) {
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
					Uri.fromParts("mailto", emailAddress, null));
			emailIntent
					.putExtra(Intent.EXTRA_SUBJECT, "Customer Contact Email");
			emailIntent.putExtra(Intent.EXTRA_TEXT, emailBodyStr);

			try {
				startActivity(Intent
						.createChooser(emailIntent, "Send email..."));
				emailSentOk = true;
				countdown(4000, 1000);
				emailBodyEt.setText("");
				emailBodyEt.setHint(R.string.mail_hint);
			} catch (android.content.ActivityNotFoundException ex) {
				ToastShooter tShoot = new ToastShooter();
				tShoot.displayToast("There are no email clients installed.",
						MailAct.this);
				emailSentOk = false;
				countdown(4000, 1000);
			}
		}
	} // end sendMail(String body)

	// COUNTDOWN TIMER
	// ******************
	// counts down 3, 2, 1 before going back to calculator screen
	public void countdown(int countdownStart, int decrementBy) {

		new CountDownTimer(countdownStart, decrementBy) {

			public void onTick(long m) {

			}

			public void onFinish() {
				mailLayoutOpen = false;
				emailMsg = true;

				if (emailSentOk) {
					String title = "Email Sent";
					CharSequence msg = "Thank you for taking the time to contact us!";
					CharSequence posTxt = "Continue";
					CharSequence negTxt = "None";
					alertDlog(title, msg, posTxt, negTxt);
				} else {
					String title = "Email Did Not Send";
					CharSequence msg = "Your email did not successfully send. Please feel free to contact us at EMAIL or PHONE";
					CharSequence posTxt = "Continue";
					CharSequence negTxt = "None";
					alertDlog(title, msg, posTxt, negTxt);
				}
			}
		}.start();
	} // end countdown()

}
