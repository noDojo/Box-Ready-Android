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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.diligencedojo.boxready2.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MoveSelFrag extends Fragment {
	private static final int REQUEST_NEW_MOVEMENT = 0;
	public static final String NEW_MOVE_ADDED = "new_move_in_list";
	public static final String MOVE_REMOVED = "move_removed";

	private GridView mGridView;
	String[] repsAdj;
	boolean allMovesUsed, moveDel, isRandom = false;
	int progPoint, moveCount, numSelLeft, ranWod = 0;

	// This array lets the user to select multiple movements. It doesn't
	// need to be re-instantiated every time the user backs up to this view.
	private ArrayList<Integer> movements = new ArrayList<Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.movement_selection, parent, false);

//		// look up the AdView as a resource and load a request
//		AdView adView = (AdView) v.findViewById(R.id.selAdView);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		try {
//			adView.loadAd(adRequest);
//		} catch (NoClassDefFoundError ex) {
//		}

		// pull intent originated in Registration with adjusted rep array
		Intent i = getActivity().getIntent();
		repsAdj = i.getExtras().getStringArray("repsAdj");
		ranWod = i.getExtras().getInt("progPoint");
		isRandom = i.getExtras().getBoolean("isRandom");
		progPoint = ranWod;
		ToastShooter tShoot = new ToastShooter();
		Movement m = new Movement();

		// set and display number of moves in WOD
		if (movements.isEmpty()) {
			numSelLeft = m.numMoves[progPoint];
			moveCount = numSelLeft;

			tShoot.displayToast("Select " + Integer.toString(moveCount),
					getActivity(), "blue");
		}

		// locate gridView and assign ImageAdapter
		mGridView = (GridView) v.findViewById(R.id.gridView);
		mGridView.setAdapter(new ImageAdapter(v.getContext()));

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// Sending image id to WODListActivity
				Intent movementIntent = new Intent(v.getContext(),
						WODListAct.class);

				movements.add(position); // Add movement's position to array

				v.setVisibility(View.GONE); // kill selected button

				movementIntent.putExtra(WODListFrag.NEW_MOVE_ADDED, movements);
				movementIntent.putExtra("moveCount", moveCount);
				movementIntent.putExtra("isRandom", isRandom);
				movementIntent.putExtra(WODListFrag.REPS_ADJ, repsAdj);
				movementIntent.putExtra("progPoint", progPoint);
				startActivityForResult(movementIntent, REQUEST_NEW_MOVEMENT);
			} // end onItemClick
		}); // end OnItemClickListener

		// this is here because the compiler complains if the
		// context we use within setOnScrollListener isn't final
		final Context tempContext = v.getContext();

		mGridView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

				// set all the image views in the grid to visible
				for (int a = 0; a < mGridView.getCount(); a++) {
					ImageView img = (ImageView) mGridView.getChildAt(a);
					if (img != null) {
						img.setVisibility(View.VISIBLE);
					}
				}

				// set button visibility based on the movements the
				// user has already used (selections saved on the
				// internal storage of the device)
				TinyDB tinydb = new TinyDB(tempContext);
				ArrayList<String> usedMoves = new ArrayList<String>();
				int usedMovesCounter = tinydb.getInt("usedMovesCounter");

				if (usedMovesCounter != 0) {
					usedMoves = tinydb.getList("usedMoves");
					usedMovesCounter = usedMoves.size() - 1;
				} else
					usedMovesCounter = 0;

				ArrayList<Integer> usedPositions = new ArrayList<Integer>();

				// Now we're going to go through the movements array and set
				// the visibility of the number in the movements list to GONE
				final int numVisibleChildren = mGridView.getChildCount();
				final int firstVisiblePosition = mGridView
						.getFirstVisiblePosition();

				// change array type from string to integer
				for (int a = 0; a < usedMoves.size(); a++)
					usedPositions.add(Integer.valueOf(usedMoves.get(a)));

				// here we run through the positions of the movements that
				// have already been used (ie. are saved to the device) and
				// set them to invisible
				for (int movementIndex = 0; movementIndex < usedPositions
						.size(); movementIndex++) {

					for (int i = 0; i < numVisibleChildren; i++) {
						int positionOfView = firstVisiblePosition + i;

						if (positionOfView == usedPositions.get(movementIndex)) {
							View view = mGridView.getChildAt(i);
							view.setVisibility(View.GONE);
						}
					} // end inner for loop
				} // end outer for loop

				// this second run through makes sure the movements that are
				// currently being selected (ie. not saved to the device) are
				// set to invisible when they are clicked
				final int firstVisiblePosition2 = mGridView
						.getFirstVisiblePosition();

				for (int movementIndex = 0; movementIndex < movements.size(); movementIndex++) {
					for (int i = 0; i < numVisibleChildren; i++) {
						int positionOfView = firstVisiblePosition2 + i;

						if (positionOfView == movements.get(movementIndex)) {
							View view = mGridView.getChildAt(i);
							view.setVisibility(View.GONE);
						}
					} // end inner for loop
				} // end outer for loop

				int buttonsGone = usedPositions.size() + movements.size();
				int temp = tinydb.getInt("buttonsGone");
				temp = buttonsGone;
				tinydb.putInt("buttonsGone", temp);

				if (buttonsGone == 10) {
					allMovesUsed = true;

					// clears all movements except the last one
					for (int s = 0; s < movements.size() - 1; s++)
						movements.remove(s);

					usedPositions.clear();
					usedMoves.clear();
					usedMovesCounter = 0;
					tinydb.putList("usedMoves", usedMoves);
					tinydb.putInt("usedMovesCounter", usedMovesCounter);

					// set all icons to visible after 2 seconds have passed
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						public void run() {
							for (int a = 0; a < mGridView.getCount(); a++) {
								ImageView img = (ImageView) mGridView
										.getChildAt(a);

								if (img != null)
									img.setVisibility(View.VISIBLE);
							} // end for loop
						}
					}, 2000);
				} // end if(buttonsGone == 10)
			} // end onScroll

			// occurs on new instance of gallery (when the view is scrolled all
			// the way to the very top or very bottom)
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {

				// set all the image views in the grid to visible
				for (int a = 0; a < mGridView.getCount(); a++) {
					ImageView img = (ImageView) mGridView.getChildAt(a);

					if (img != null)
						img.setVisibility(View.VISIBLE);
				}

				// Now we're going to go through the movements array and set
				// the visibility of the number in the movements list to GONE
				final int numVisibleChildren = mGridView.getChildCount();
				final int firstVisiblePosition = mGridView
						.getFirstVisiblePosition();

				for (int movementIndex = 0; movementIndex < movements.size(); movementIndex++) {
					for (int i = 0; i < numVisibleChildren; i++) {
						int positionOfView = firstVisiblePosition + i;

						if (positionOfView == movements.get(movementIndex)) {
							View view = mGridView.getChildAt(i);
							view.setVisibility(View.GONE);
						}
					} // end inner for loop
				} // end outer for loop
			} // end onScrollStateChanged
		});

		return v;
	} // end onCreateView()

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putString("saved", "saved");
		savedInstanceState.putIntegerArrayList("msfArray", movements);
		savedInstanceState.putAll(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		if (requestCode == REQUEST_NEW_MOVEMENT) {

			movements = data
					.getIntegerArrayListExtra(WODListFrag.NEW_MOVE_ADDED);

			// set all the image views in the grid to visible
			for (int a = 0; a < mGridView.getCount(); a++) {
				ImageView img = (ImageView) mGridView.getChildAt(a);
				if (img != null) {
					img.setVisibility(View.VISIBLE);
				}
			}

			// Now we're going to go through the movements array and set
			// the visibility of the number in the movements list to GONE
			final int numVisibleChildren = mGridView.getChildCount();
			final int firstVisiblePosition = mGridView
					.getFirstVisiblePosition();

			for (int movementIndex = 0; movementIndex < movements.size(); movementIndex++) {
				for (int i = 0; i < numVisibleChildren; i++) {
					int positionOfView = firstVisiblePosition + i;

					if (positionOfView == movements.get(movementIndex)) {
						View view = mGridView.getChildAt(i);
						view.setVisibility(View.GONE);
					}
				} // end inner for loop
			} // end outer for loop
		}
	} // end onActivityResult()
}
