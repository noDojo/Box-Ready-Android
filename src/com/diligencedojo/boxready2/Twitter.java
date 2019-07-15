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

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import com.diligencedojo.boxready2.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService.Session;
import android.view.Window;
import android.widget.Toast;

public class Twitter {
	private Context mContext;
	private SocialAuthAdapter mAdapter = null;
	private ProgressDialog mDialog;
	private String mProviderName;
	private Profile mUserProfile;
	private String textName;
	private String postMsg = "I just finished this workout!  #onestepcloser\n@BoxReady";

	public Twitter(Context context) {
		mAdapter = new SocialAuthAdapter(new ResponseListener());
		init();

		mContext = context;
	}

	// Initial configuration of the twitter adapter.
	private void init() {
		mAdapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		mAdapter.addCallBack(Provider.TWITTER, Constants.TWITTER_CLB);

		try {
			mAdapter.addConfig(Provider.TWITTER, Constants.TWITTER_KEY,
					Constants.TWITTER_SEC, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateStatus(final Bitmap picToPost, final String picFileName) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					mAdapter.uploadImage(postMsg, picFileName, picToPost, 100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		new Thread(runnable).start();
	}

	public void authorize() {
		mAdapter.authorize(mContext, Provider.TWITTER);
	}

	public void logout() {
		mAdapter.signOut(mContext, Provider.TWITTER.toString());
	}

	private String getUserName() {
		return mUserProfile.getDisplayName();
	}

	public boolean isAuthorized() {
		String token = null;
		try {
			token = mAdapter.getCurrentProvider().getAccessGrant().getKey();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (token == null)
			return false;
		else
			return true;
	}

	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			// Get the provider
			mProviderName = values.getString(SocialAuthAdapter.PROVIDER);

			// allows access to the user's info //
			// mUserProfile = mAdapter.getUserProfile();
			// updateStatus();
			mDialog = new ProgressDialog(mContext);
			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mDialog.setMessage("Loading...");
		}

		@Override
		public void onBack() {
		}

		@Override
		public void onCancel() {
		}

		@Override
		public void onError(SocialAuthError arg0) {
		}
	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201
					|| status.intValue() == 204)
				Toast.makeText(mContext, "Message posted on " + provider,
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(mContext, "Message not posted " + provider,
						Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(SocialAuthError e) {
		}
	}

	// To receive the profile response after authentication
	private final class ProfileDataListener implements
			SocialAuthListener<Profile> {
		@Override
		public void onError(SocialAuthError arg0) {
		}

		@Override
		public void onExecute(String arg0, Profile t) {
			mUserProfile = t;
		}
	}
}