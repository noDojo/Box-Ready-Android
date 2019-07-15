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

public class TermArrays {
	// Header array
	String[] headers = { "Basics", "Movements", "Workouts", "Scoring Methods",
			"Equipment", "Lifts" };

	// Term arrays
	String[] basics = { "Box", "Buttercup", "Calculate Distance", "Movement",
			"PR", "Rep", "RM", "Round", "RX", "Scaling", "Set", "WOD" };
	String[] movements = { "Air Squats", "Box Jumps", "Burpees",
			"Double Unders", "HSPU", "K2E", "Mountain Climbers", "Muscle Ups",
			"Pistols", "Pull Ups", "Push Ups", "Ring Dips", "Running",
			"Sit Ups", "T2B", "Walking Lunges", "Wallballs" };
	String[] workouts = { "AMRAP", "Benchmark Workout", "EMOM", "Hero Workout",
			"ME", "MetCon", "Tabata" };
	String[] scoring = { "For Rounds", "For Reps", "For Time" };
	String[] equipment = { "Ab Mat", "Bumper Plates", "KB", "Medicine Ball",
			"Pood", "Rings" };
	String[] lifts = { "Bench Press", "Back Squat", "Clean", "Clean & Jerk",
			"Deadlift", "Front Squat", "Jerk", "OHS", "Push Press", "SDHP",
			"Snatch", "Split Jerk", "Thruster" };

	// string arrays housing the definitions for each group
	String[] basicsArray = {
			// BOX
			// ******
			"A gym that offers CrossFit training.\n",
			// BUTTERCUP
			// ************
			"This is just an easy way to scale a workout.  Either the number of reps or the time allotted for a workout are cut in half.\n\n100 reps becomes 50 reps\n20 min. workout becomes 10 min.",
			// CALCULATE DISTANCE
			// *********************
			"When running, distance can be approximated in steps.\n\n    1 mile = 2,000 steps\n1/2 mile = 1,000 steps\n1/4 mile =    500 steps\n1/8 mile =    250 steps\n",
			// MOVEMENT
			// ***********
			"A single excerise.  Examples would be push ups, or pull ups, or squats (just to name a few).\n",
			// PR
			// *****
			"\"Personal Record\"\nWhen an athlete achieves his or her personal best on a lift or workout.\n",
			// REP
			// ******
			"\"Repetition\"\nPerforming a movement once.  For example, 25 reps of push ups would mean you did 25 push ups.\n",
			// RM
			// *****
			"\"Repetition Maximum\"\nThe most weight you can lift for a defined number of exercise movements.  A 1 RM, for example, is the heaviest weight you can lift if you give it your maximum effort.  A 10RM would be the heaviest weight you could lift for 10 consecutive reps.\n",
			// ROUND
			// ********
			"One set each of a combination of given exercises.  Generally, this means completing the assigned reps for each movement in a list (finishing the list once would equal one round).\n",
			// RX
			// *****
			"\"As Prescribed\"\nWhen a workout is RX�d, it means the athlete did all of the assigned movements using the prescribed weight and doing the prescribed number of reps.\n",
			// SCALING
			// **********
			"Done when an athlete cannot, or should not (due to injury, extreme soreness, etc), complete a workout as it has been prescribed.  Scaling a workout can be done numerous ways.  An athlete can scale a workout by doing less weight, less reps, less rounds, or movements that are similar to the prescribed movements.\n",
			// SET
			// ******
			"A group of reps for a given exercise.  For example, 3 sets of 25 reps means to do 75 total reps split into 25 rep \"sets\".  Typically, the athlete rests between each set\n",
			// WOD
			// ******
			"\"Workout of the Day\"\nMany gyms have a workout written on a white board and/or posted online for their members each day."};
	String[] movementsArray = {
			// AIR SQUATS
			// *************
			"A full squat with no extra weight (barbell or dumbbells).\nStart from the standing position with feet slightly wider than shoudler width."
					+ "  Keep your knees directly over your toes throughout the movement."
					+ "  Lower body into a squat, keeping the chest up and eyes forward.  Your butt should be at, or "
					+ "below parallel with your knees at the bottom of the movment.\nReturn to the standing position."
					+ "\nRepeat.\n",
			// BOX JUMPS
			// ************
			"Stand in front of the box with feet directly under the hips."
					+ "  Lower slightly into a jumping position.  Keep your head up and back straight."
					+ "  Explosively jump from the crouched position (swing your arms to make the jump easier)."
					+ "  Land as softly as possible on the box, absorbing the impact with your legs."
					+ "  Stand up straight, locking the knees and hips."
					+ "  Jump or step down from the box and repeat.\n",
			// BURPEES
			// **********
			"Start in standing position and move to a push up position.  At the bottom of the movement, the chest and hips must touch the ground.  From this position, "
					+ "return to a standing posititon and jump, clapping your hands above head (jump must be a minimum of "
					+ "6 inches off the ground).  Repeat.\n",
			// DU
			// *****
			"When a jump rope passes under an athlete�s feet twice with only one jump.\n",
			// HSPU
			// *******
			"\"Hand Stand Push Ups\"\nDo a handstand against the wall.  Lower the body until your head touches the floor and then push upwards until the arms are fully locked.\n",
			// K2E
			// ******
			"\"Kness to Elbows\"\nWhile hanging from a pull up bar, lift the knees up toward the torso until the elbows and knees touch.\n",
			// MTN CLIMBERS
			// ***************
			"Assume a pushup position with your arms straight and your body in a straight line from your head to your ankles. Bring your right knee to your chest and place your foot back on the ground behind you. Quickly switch to the other leg and repeat. It will seem as though you are running in the pushup position. Alternate back and forth as fast as you can.\n",
			// MU
			// *****
			"This is a movement taken from gymnastics.  An athlete hangs from gymnastic rings and explosively pulls their chest above the rings (swinging the legs helps create momentum). When the athlete\'s chest is above the rings, they roll their body slightly forward into a dip position while still holding on to the rings.  From this position they push up until the arms are fully locked.\n",
			// PISTOLS
			// **********
			"A single leg squat.  It is important to keep your heels on the floor while doing this movement.  Pistols develop balance and core strength.\n",
			// PULL UPS
			// ***********
			"Hang from the bar with arms fully extended.  Pull body upward until the chin"
					+ " is above the bar.  Lower body until arms are fully extended.\nRepeat.\n",
			// PUSH UPS
			// ***********
			"Lie face down on the floor with hands slightly wider than shoulder width."
					+ "  Push upwards until your arms are completely extended, moving the body as one unit."
					+ "  Lower the body until the chest brushes the floor.  Repeat.  It is important that you keep your body flat throughout the entire movement.\n",
			// RING DIPS
			// ************
			"Like a conventional bodyweight dip, only on gymnastic rings. The athlete holds the body in such a way that the chest is even with the rings and the arms are at a 90 degree angle.  They push upwards until their arms are locked, then lower themselves back into the starting position.  The rings are unstable, making it harder to keep the hands close to the body throughout the movement.\n",
			// RUNNING
			// **********
			"Moving your legs at a speed that is faster than walking.\n",
			// SIT UPS
			// **********
			"Lie on your back with arms folded across your chest and use your stomach muscles to raise the top part of your body to a sitting position.  The shoulder blades should touch the floor at the bottom of the movement and the elbows should touch knees at the top.\n",
			// T2B
			// ******
			"\"Toes to Bar\"\nWhile hanging from a pull up bar, lift the feet up toward the bar until they touch the bar.\n",
			// WALKING LUNGES
			// *****************
			"Start from the standing position."
					+ "Step far forward with one leg while simultaneously lifting up onto the ball of the back foot."
					+ "  Keeping your chest up and shoulders back, bend the knees and drop your hips downwards straight to the ground."
					+ "  Press up with your front leg and bring your back foot forward."
					+ "  Step forward with the opposite foot and repeat with the other foot.\n",
			// WALLBALLS
			// ************
			"With a weighted ball (typically a medicine ball), descend into a squat and explosively heave the ball upwards at the wall during the upwards movement of the squat.  As the ball falls back to you, catch it while simultaneously dropping back into a squat.  Now you can repeat the movement.\n" };
	String[] workoutsArray = {
			// AMRAP
			// ********
			"\"As Many Rounds As Possible\"\nAMRAP's challenge athletes to complete as many rounds of a series of movements in a specified time.\n",
			// BENCHMARK
			// ************
			"A workout where you can collect measurable, repeatable data to track your fitness progress. There are many standard Benchmark WODs.\n",
			// EMOM
			// *******
			"\"Every Minute On the Minute\"\nDo the designated number of reps every minute. If you finish before the minute is up, then you have the remainder of the minute to rest.\n",
			// HEROES
			// *********
			"Workouts named after military servicemen, law enforcement, or firefighters who have died in the line of duty.  These difficult workouts provide an extra challenge and remind us of their sacrifice.\n",
			// ME
			// *****
			"\"Max Effort\"\nWorkout that consists of performing an movement or a series of movements to achieve muscle failure.\n",
			// METCON
			// *********
			"\"Metabolic Conditioning\"\nWorkouts designed to increase stamina, endurance, and conditioning.  Metcons generally include some sort of timed component to encourage the athlete to train at high intensity.\n",
			// TABATA
			// *********
			"A type of high intensity interval training.  The athlete does 20 seconds of a very high intensity exercise, 10 seconds of rest.\nRepeat 8 times for a total of 4 minutes\n" };
	String[] scoringArray = {
			// FOR ROUNDS
			// *************
			"The total number of rounds completed during a timed workout.\n",
			// FOR REPS
			// ***********
			"The total number of reps completed during a given workout.  Typically these workouts will be timed, but they may also be until muscle failure.\n",
			// FOR TIME
			// ***********
			"The amount of time it takes to complete a workout.  It helps to try to beat your previous time for the specific workout you're doing or find someone to race against.\n" };
	String[] equipmentArray = {
			// AB MAT
			// *********
			"A contoured foam wedge placed behind the back during sit-ups.  The abmat allows a greater range of motion while also providing padding between the athlete and the ground.\n",
			// BUMPER PLATES
			// ****************
			"Rubberized barbell plates that allow athletes to drop weights if necessary.\n",
			// KB
			// *****
			"\"Kettlebell\"\nA round weight with a flat bottom and thick handle on top.  Basically, it's a cannonball with a handle.\n",
			// MEDICINE BALL
			// ****************
			"A large, heavy sand-packed ball thrown and caught for exercise.\n",
			// POOD
			// *******
			"A unit of measurement used for Kettlebell weights.\n\n1 pood = 35 pounds.\n",
			// RINGS
			// ********
			"Gymnastic rings used for a wide range of movements including dips, rows, and muscle-ups.\n" };
	String[] liftsArray = {
			// BP
			// *****
			"The lifter lies back on a bench with both feet flat on the floor.  The weight is lowered to their chest and then pushed back up until the arms are fully extended.\n",
			// BS
			// *****
			"Start in the standing position with a weight across the shoudlers.  From this position squat down until the rear end is even with the knees.  At the bottom of the squat, stand back up.  At the top of the squat the hips and knees must both be locked.\n",
			// CLEAN
			// ********
			"Movement where the athlete explosively lifts a weighted barbell from the floor to a racked position across the upper chest.\n",
			// C&J
			// ******
			"Athletes start by explosively lifting a weighted barbell from the ground to the shoulders, often squatting under the bar to catch it and then standing to recover. After a brief pause, athletes take a shallow dip and then drives the arms upward to propel the bar overhead.  The hips and knees must be locked when the arms are fully extended at the top of the lift.\n",
			// DL
			// *****
			"A lift in which a loaded barbell is lifted off the ground to the hips, then lowered back to the ground.\n",
			// FS
			// *****
			"Movement where the weight (usually a barbell) is held in front of the body across the upper chest in a clean grip, while a squat is performed.\n",
			// JERK
			// *******
			"Start with the weight in the racked position on the front of your shoulders and top of the chest. Begin the movement by dipping into a quarter-squat and driving the barbell to the overhead position as you catch the bar in a partial squat. Lock the knees, hips, and elbows to complete the movement.\n",
			// OHS
			// ******
			"A squat while holding a barbell weight overhead with the arms locked.  Requires a great deal of core strength.\n",
			// PP
			// *****
			"Hold the barbell in front of the body across the upper chest with the grip slightly wider than shoulder width. Dip body by bending knees, hips and ankles slightly. Explosively drive upward with legs, driving barbell up off shoulders and vigorously extending arms overhead. Return to shoulders and repeat.\n",
			// SDHP
			// *******
			"Take a wide stance over a barbell and pull from the ground upward until the bar comes up to shoulder height.  Return the bar to the floor and repeat.\n",
			// SNATCH
			// *********
			"An olympic lift where athletes explosively lift a weighted barbell from ground to overhead in one movement, often squatting under the bar and then standing up.\n",
			// SPLIT JERK
			// *************
			"A barbell is held in front of the body across the upper chest with the grip slightly wider than shoulder width. Keeping pressure on the heels, dip body by bending knees and ankles slightly.  Explosively drive upward with legs, driving barbell up off shoulders. Drop body downward and split one foot forward and other backward as fast as possible while vigorously extending arms overhead."
					+ "The split position places front shin vertical to floor with front foot flat on floor. The rear knee is slightly bent with rear foot positioned on toes. The bar should be positioned directly over ears with the arms locked and the back straight. Push up with both legs. Position feet side by side by bringing front foot back part way and then rear foot forward. Return to shoulders and repeat.\n",
			// THRUSTERS
			// ************
			"A front squat that transitions directly into a push press.  The lift begins and ends when the bar touches the front of the shoulders.\n" };

	// ARRAY OF STRING ARRAYS (HOLDS EACH OF THE ABOVE ARRAYS)
	// **********************************************************
	String[][] definitions = { basicsArray, movementsArray, workoutsArray,
			scoringArray, equipmentArray, liftsArray };
}
