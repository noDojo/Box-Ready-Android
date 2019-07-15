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
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.diligencedojo.boxready2.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class TermAct extends Activity {

	TermListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	boolean expanded = false;
	TextView itemDef;
	private int lastExpandedPosition, lastExpandedChild = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.term_main);

//		// look up the AdView as a resource and load a request
//		AdView adView = (AdView) findViewById(R.id.termAdView);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		try {
//			adView.loadAd(adRequest);
//		} catch (NoClassDefFoundError ex) {
//		}

		// get the list view
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		// setting list adapter
		listAdapter = new TermListAdapter(this, listDataHeader, listDataChild);
		expListView.setAdapter(listAdapter);

		// List view Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				return false;
			}
		});

		// On group expand listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				// collapses all groups other than the current selected
				if (lastExpandedPosition != -1
						&& groupPosition != lastExpandedPosition) {
					expListView.collapseGroup(lastExpandedPosition);
				}
				lastExpandedPosition = groupPosition;
			}
		});

		// On group collapse listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				// get rid of persisting text view values
				if (itemDef != null) {
					itemDef.setVisibility(View.GONE);
					expanded = false;
				}
			}
		});

		// List view on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				TermArrays tA = new TermArrays();

				// collapses all children other than the current selected
				if (lastExpandedChild != -1
						&& childPosition != lastExpandedChild) {
					itemDef.setVisibility(View.GONE);
					expanded = false;
				}
				lastExpandedChild = childPosition;

				itemDef = (TextView) v.findViewById(R.id.term_def);

				if (expanded == false) {
					itemDef.setText(tA.definitions[groupPosition][childPosition]);
					itemDef.setVisibility(View.VISIBLE);
					expanded = true;
				} else {
					itemDef.setVisibility(View.GONE);
					expanded = false;
				}

				return false;
			}
		});
	}

	// PREPARE LIST DATA
	// ********************
	private void prepareListData() {
		TermArrays tA = new TermArrays();
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Add headers
		for (int i = 0; i < tA.headers.length; i++)
			listDataHeader.add(tA.headers[i]);

		// Add terms
		List<String> basicsList = new ArrayList<String>();
		for (int i = 0; i < tA.basics.length; i++)
			basicsList.add(tA.basics[i]);
		List<String> movementsList = new ArrayList<String>();
		for (int i = 0; i < tA.movements.length; i++)
			movementsList.add(tA.movements[i]);
		List<String> workoutsList = new ArrayList<String>();
		for (int i = 0; i < tA.workouts.length; i++)
			workoutsList.add(tA.workouts[i]);
		List<String> scoringList = new ArrayList<String>();
		for (int i = 0; i < tA.scoring.length; i++)
			scoringList.add(tA.scoring[i]);
		List<String> equipmentList = new ArrayList<String>();
		for (int i = 0; i < tA.equipment.length; i++)
			equipmentList.add(tA.equipment[i]);
		List<String> liftsList = new ArrayList<String>();
		for (int i = 0; i < tA.lifts.length; i++)
			liftsList.add(tA.lifts[i]);

		// Header, Child data
		listDataChild.put(listDataHeader.get(0), basicsList);
		listDataChild.put(listDataHeader.get(1), movementsList);
		listDataChild.put(listDataHeader.get(2), workoutsList);
		listDataChild.put(listDataHeader.get(3), scoringList);
		listDataChild.put(listDataHeader.get(4), equipmentList);
		listDataChild.put(listDataHeader.get(5), liftsList);
	}
}