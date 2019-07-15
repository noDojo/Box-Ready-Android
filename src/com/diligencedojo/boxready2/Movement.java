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

public class Movement {
	String mMovementName;
	Integer mReps;
	String mName;
	String[] mWod;
	Boolean mStart;
	Integer mTotMoveCount;

	@Override
	public String toString() {
		return mMovementName;
	}

	public void skillMovement() {
		mMovementName = movementNames[0];
	}

	public void setMovementName(String movementName) {
		mMovementName = movementName;
	}

	public String getMovementName() {
		return mMovementName;
	}

	public void setReps(Integer reps) {
		mReps = reps;
	}

	public Integer getReps() {
		return mReps;
	}

	// number of movements that make up each wod.
	// *Skill Day is represented by 0 to allow for back button lock*
	Integer[] numMoves = { 3, 2, 1, 4, 2, 0, 2, 1, 3, 2, 0, 5, 4, 2, 0, 2, 2,
			2, 4, 0, 3, 3, 2, 5 };

	String[] movementNames = { "Push Ups", "Pull Ups", "Burpees", "Air Squats",
			"Sit Ups", "Mountain Climbers", "Run", "Jump Rope",
			"Walking Lunges", "Box Jumps" };

	String[] skills = { "Double-Unders", "HSPU Progression",
			"Overhead PVC Squats", "Pull ups" };

	public String getMovList(Integer position) {
		return movementNames[position];
	}

	// This is the base rep scheme. It is based on a user that is male,
	// under 30, and in average shape.
	String[] reps = { "25", "20", "20", "30", "25", "30", "1/4 mile", "75",
			"40", "30" };

	public String getReps(Integer position) {
		return reps[position];
	}

	// week 1 work outs (free version)
	String[] diffWODstyles = { "3 ROUNDS FOR TIME", "21-15-9", "MAX REPS" };

	// full 8 week progression (upgraded version)
	String[] wodStylesUpgraded = { "3 ROUNDS FOR TIME", "21-15-9", "MAX REPS",
			"BACK TO BACK", "EMOM: 7 MIN", "SKILL DAY: 15 MIN",
			"RAPID DESCENT", "DEATH BY", "3 ROUNDS FOR TIME", "10 MIN AMRAP",
			"SKILL DAY: 20 MIN", "THE FIFTY", "ANGIE", "ANNIE",
			"SKILL DAY: 25 MIN", "NICOLE", "EMOM: 10 MIN", "21-15-9",
			"HERO WOD: LOREDO", "SKILL DAY: 30 MIN", "CINDY",
			"3 ROUNDS FOR TIME", "ANNIE", "HERO WOD: MURPH" };

	// rep scheme for each specific work out. rep adjusted work outs are
	// represented by "x"
	String[] wodReps = { "x", "21-15-9", "Max in 7 minutes", "Max in 1 minute",
			"x", "15 minutes of work", "10, 9, 8, 7, 6, 5, 4, 3, 2, 1",
			"Do 1 rep for round 1,\n\t\t\t\t2 reps for round 2, etc...", "x",
			"x", "20 minutes of work", "50", "100", "50-40-30-20-10",
			"25 minutes of work", "Max Reps\n\t  (without releasing the bar)",
			"x", "21-15-9", "24", "30 miuntes of skill work", "Cindy: ", "x",
			"50-40-30-20-10", "Murph: " };

	// ALOT OF THIS STILL NEEDS TO BE FILLED IN //
	String[] wodScoring = {
			// 3 ROUNDS
			// ***********
			"Your score is the time it takes you to complete the list from top to bottom 3 times.\n\nRest as needed.\n",
			// 21-15-9
			// **********
			"Complete the list doing 21 reps per movement, then again doing 15 reps per movement, "
					+ "and one last time doing each movement 9 times.  Your score is the time it takes to complete the workout.\n\nRest as needed.\n",
			// MAX REPS
			// ***********
			"Do as many reps of this movement as possible until the clock stops.  Your score is the number of reps you complete during this time.\n\nRest as needed.\n",
			// BACK TO BACK
			// ***************
			"This workout is designed to give you a feel for a workout named \"Fight Gone Bad\".  Do the list 3 times.  The clock does not reset or stop between exercises.  On the turn of each minute, the athlete must move on to next movement.  The total number of reps for all movements is the score.  Each round is followed by a minute of rest, after which the list of movements is restarted.\n\nRest as needed.\n",
			// EMOM
			// *******
			"\"Every Minute On the Minute\"\nComplete the list starting on the minute and resting until the turn of the next minute, then repeat.  This workout is not scored.\n\nRest as needed.\n",
			// SKILL DAY 15
			// ***************
			"Work on the designated skill for 15 minutes.  View the video for pointers towards improvement.  This workout is not scored.\n\nRest as needed.\n",
			// REPS DOWN
			// ************
			"Complete the list at 10 reps per movement, then again at 9 reps per movement, again at 8 reps per movement, and so on until you complete the list at 1 rep per movement.  Your score is the time it takes to complete the workout.\n\nRest as needed.\n",
			// DEATH BY
			// ***********
			"Complete one rep on the start of the first minute, two reps on the second minute, three reps on the third minute, and so on until you can no longer complete the movement for the number of minutes on the timer.  Your score is the number of minutes you can go before you can't complete the amount of reps in a minute.\n\nRest as needed.\n",
			// 3 ROUNDS
			// ***********
			"Your score is the time it takes you to complete the list from top to bottom 3 times.\n\nRest as needed.\n",
			// AMRAP 10
			// ***********
			"Each time you complete the list counts as one round.  Your score is the number of rounds completed in the allotted time.\n\nRest as needed.\n",
			// SKILL DAY 20
			// ***************
			"Work on the designated skill for 20 minutes.  View the video for pointers towards improvement.  This workout is not scored.\n\nRest as needed.\n",
			// THE FIFTY
			// ************
			"This workout is designed to give you a feel for a workout named \"The Filthy Fifty\".  Complete the list once as quickly as possible.  Your score is the time on the clock when you finish.\n\nRest as needed.\n",
			// ANGIE
			// ********
			"Complete the list from top to bottom as quickly as possible.  All reps for each movement must be completed before moving to the next.  Your score is the time it takes to complete the workout.\n\nRest as needed.\n",
			// ANNIE
			// ********
			"This workout is meant to be done with little or no rest.  The decrease in reps on each round completed is meant to fatigue the athlete just enough so that each round feels as difficult as the previous.\n\nRest as needed.\n",
			// SKILL DAY 25
			// ***************
			"Work on the designated skill for 25 minutes.  View the video for pointers towards improvement.  This workout is not scored.\n\nRest as needed.\n",
			// NICOLE
			// *********
			"Nicole is a benchmark wod.  Do as many rounds as possible in 20 minutes.  Your score is the total number of pull ups completed during the workout.\n\nRest as needed.\n",
			// EMOM
			// *******
			"\"Every Minute On the Minute\"\nComplete the reps for each movement starting on the minute and resting until the turn of the next minute, then repeat.  This workout is not scored.\n\nRest as needed.\n",
			// 21-15-9
			// **********
			"Complete the list doing 21 reps per movement, then again doing 15 reps per movement, "
					+ "and one last time doing each movement 9 times.  Your score is the time it takes to complete the workout.\n\nRest as needed.\n",
			// LOREDO
			// *********
			"The hero wod Loredo is named for the fallen soldier U.S. Army Staff Sergeant Edwardo Loredo.  Do the list from top to bottom 6 times.  Your score is the time it takes to complete the workout.\n\nRest as needed.\n",
			// SKILL DAY 30
			// ***************
			"Work on the designated skill for 30 minutes.  View the video for pointers towards improvement.  This workout is not scored.\n\nRest as needed.\n",
			// CINDY
			// ********
			"Cindy is a benchmark wod.  Do as many rounds as possible in 20 minutes.  Your score is the total number of rounds you complete during that time.\n\nRest as needed.\n",
			// 3 ROUNDS
			// ***********
			"Your score is the time it takes you to complete the list from top to bottom 3 times.\n\nRest as needed.\n",
			// ANNIE
			// ********
			"This workout is meant to be done with little or no rest.  The decrease in reps on each round completed is meant to fatigue the athlete just enough so that each round feels as difficult as the previous.  Your score is the time it takes to complete the workout.\n\nRest as needed.\n",
			// MURPH
			// ********
			"The hero wod Murph is named for the fallen soldier U.S. Navy Lieutenant Michael Murphy.  Complete the list of movements as quickly as possible.  The movements (not including the runs) can be done in any order.  For instance, many people use a 5-10-15 split.  This means they do 5 pull ups, 10 push ups, and then 15 squats.  Using this strategy, you would do 20 rounds of 5-10-15, completing the run before and after also.  By using a strategy for this workout, you can try to avoid burnout while maintaining activity.  Your score is the time it takes to complete the workout.\n\nRest as needed.\n" };

	public void setWodStyle(Integer totMovCount) {
		mTotMoveCount = totMovCount;
	}

	public Integer getWodStyle() {
		return mTotMoveCount;
	}

	String[] chooseMovInstr = { "Select 3 movements.", "Select 2 movements",
			"Choose " + "1 movement" };

	// array of stop times for the work outs with set stop times
	Integer[] whenToStop = { 7, 15, 7, 15, 10, 20, 25, 20, 10, 30, 20 };
	// **test array** //
	// Integer[] whenToStop = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

}
