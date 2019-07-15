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
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;

import com.github.johnpersano.supertoasts.SuperToast;

public class ToastShooter {

	// DISPLAY TOASTS
	// *****************
	public void displayToast(String message, Activity displayAct) {

		SuperToast superToast = new SuperToast(displayAct);
		superToast = new SuperToast(displayAct);
		superToast.setAnimations(SuperToast.Animations.FLYIN);
		superToast.setText(message);
		superToast.setTextSize(SuperToast.TextSize.MEDIUM);
		superToast.setTypefaceStyle(Typeface.BOLD);
		superToast.setGravity(Gravity.CENTER, 0, 0);
		superToast.setBackground(SuperToast.Background.GREEN);
		superToast.setTextColor(Color.BLACK);
		superToast.setDuration(SuperToast.Duration.VERY_SHORT);
		superToast.show();
	}

	// DISPLAY TOASTS
	// *****************
	public void displayToast(String message, Activity displayAct, String color) {

		SuperToast superToast = new SuperToast(displayAct);
		superToast = new SuperToast(displayAct);
		superToast.setAnimations(SuperToast.Animations.FLYIN);
		superToast.setText(message);
		superToast.setTextSize(SuperToast.TextSize.MEDIUM);
		superToast.setTypefaceStyle(Typeface.BOLD);
		superToast.setGravity(Gravity.CENTER, 0, 0);
		superToast.setBackground(SuperToast.Background.BLUE);
		superToast.setTextColor(Color.GREEN);
		superToast.setDuration(SuperToast.Duration.VERY_SHORT);
		superToast.show();
	}

	// DISPLAY TOASTS W/ ICONS
	// **************************
	public void displayToast(Integer resId, String message, Activity displayAct) {

		SuperToast superToast = new SuperToast(displayAct);
		superToast = new SuperToast(displayAct);
		superToast.setAnimations(SuperToast.Animations.FLYIN);
		superToast.setText(message);
		superToast.setTextSize(SuperToast.TextSize.MEDIUM);
		superToast.setTextColor(Color.BLACK);
		superToast.setTypefaceStyle(Typeface.BOLD);
		superToast.setBackground(SuperToast.Background.GREEN);
		superToast.setGravity(Gravity.CENTER, 0, 0);
		superToast.setIcon(resId, SuperToast.IconPosition.LEFT);
		superToast.setDuration(SuperToast.Duration.SHORT);
		superToast.show();
	}

}
