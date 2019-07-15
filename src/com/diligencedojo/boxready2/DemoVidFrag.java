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
import java.util.ArrayList;

import com.diligencedojo.boxready2.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class DemoVidFrag extends Fragment {

	boolean skillDay = false;
	boolean hasUpgrade = true;
	int move, progPoint = 0;
	VideoView mVideo;
	private Button mPlayButton;
	// private Uri videoUri = Uri.parse("android.resource://"
	// + "com.diligencedojo.boxready/" + R.raw.push_ups);

	Uri[] videos = { // movement videos
			Uri.parse("http://www.youtube.com/watch?feature=player_detailpage&v=2mcqHMNoH_4"),
			Uri.parse("http://www.youtube.com/watch?v=XSMgkNc17A4"),
			Uri.parse("http://www.youtube.com/watch?v=7Do3zchsH0I"),
			Uri.parse("http://www.youtube.com/watch?v=_-6Agfc88sY"),
			Uri.parse("https://www.youtube.com/watch?v=YfBdc5xLa7g"),
			Uri.parse("http://www.youtube.com/watch?v=1J4hRICVjRo"),
			Uri.parse("http://www.youtube.com/watch?feature=player_detailpage&v=wRkeBVMQSgg"),
			Uri.parse("http://www.youtube.com/watch?feature=player_detailpage&v=LsWui2L_r2c"),
			Uri.parse("http://www.youtube.com/watch?v=n_xR8h2eCBI"),
			Uri.parse("http://www.youtube.com/watch?feature=player_detailpage&v=IxrzCG_7FH4") };
	Uri[] skillVids = { // skill videos
			Uri.parse("https://www.youtube.com/watch?v=3Ql37eJVwwM"),
			Uri.parse("http://www.youtube.com/watch?v=cbqHHcTHKdo"),
			Uri.parse("https://www.youtube.com/watch?v=mrLUG_UyvV0"),
			Uri.parse("https://www.youtube.com/watch?v=QrigE0M7j4o") };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.demo_vid, parent, false);

		mPlayButton = (Button) v.findViewById(R.id.demo_playButton);
		mVideo = (VideoView) v.findViewById(R.id.video_view);
		mVideo.setMediaController(new MediaController(getActivity()));

		Intent i = getActivity().getIntent();
		move = i.getExtras().getInt("move");
		TinyDB tinydb = new TinyDB(v.getContext());
		progPoint = tinydb.getInt("progPoint");

		// PLAY BUTTON
		// **************
		mPlayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// if it's a skill day, use the skillVids array
				if (progPoint == 5)
					startActivity(new Intent(Intent.ACTION_VIEW, skillVids[0]));
				else if (progPoint == 10)
					startActivity(new Intent(Intent.ACTION_VIEW, skillVids[1]));
				else if (progPoint == 14)
					startActivity(new Intent(Intent.ACTION_VIEW, skillVids[2]));
				else if (progPoint == 19)
					startActivity(new Intent(Intent.ACTION_VIEW, skillVids[3]));
				// if not a skill day, use the videos array
				else
					startActivity(new Intent(Intent.ACTION_VIEW, videos[move]));
			}
		});

		return v;
	}
}
