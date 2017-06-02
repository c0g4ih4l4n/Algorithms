
import java.lang.Math;
import java.util.*;

// using loop

public class TSP {

	public static void main (String[] args) {
		int n     = 10;
		int[][] a = new int[n][n];

		Init.initCost (a, n);



		State loopState = new State (n);
		Init.initState (loopState);
		State result = new State (n);
		Init.initState (result); result.calculateTSP (a);


		// loop all element
		do {
			loopState.calculateTSP (a);
			State.updateResult (result, loopState);
		} while (loopState.nextState());
		result.print();

		System.out.println ();



		// use Population
		Population population = new Population (n);
		population.calculateTSP (a);

		int loop = 0;
		do {
			loop ++;
			population.generate (a);
		} while (loop < 500);
		System.out.println ("AVG TSP : " + population.avgTSP);
		State resultGA = population.getBestState ();
		resultGA.print();


		// use DGEA
		Population populationDGEA = new Population (n);
		populationDGEA.calculateTSP (a);

		loop = 0;
		mode = "Expolit";
		float dlow, dhigh;
		while (loop < 500) {
			loop ++;

			float diversityValue = calcDiversity (p);
			if (diversityValue < dlow)
				mode = "Explore";
			else if (diversityValue > dhigh)
				mode = "Exploit";

			if (mode == "Exploit") {
				// combine
			} else {
				// mutate
			}
			// calc population
		}


	}
}

class Init {

	// init cost of each road
	public static void initCost (int[][] a, int n) {

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					a[i][j] = -1;
				} else a[i][j] = (int) (Math.random() * 100);
			}
		}
		// end init array Cost
	}

	public static void initState (State state) {
		int n = state.x.length;
		for (int i = 0; i < n; i++) {
			state.x[i] = i;
		}
	}

	// initialize random combination State
	// for population
	public static void initRandomState (State state) {
		int n = state.x.length;
		List<Integer> list = new ArrayList<>();

		// loop to add value to list and shuffle it
		for (int i = 1; i < n; i++) {
			list.add (i);
		}
		Collections.shuffle (list);

		// pass list was shuffled to State
		state.x[0] = 0;
		for (int i = 1; i < n; i++) {
			state.x[i] = list.get(i - 1);
		}
	}
}


class Population {

	ArrayList<State> 	list;
	double 				avgTSP;
	int 				m;
	double 				delta;
	ArrayList<State> 	parents;
	ArrayList<Mutation> mutations;
	ArrayList<Hybrid> 	hybrids;

	// number generation
	// for terminate condition
	int                 numberGeneration;

	// initialize random state
	public Population (int n) {
		list 		= new ArrayList<>();
		parents 	= new ArrayList<>();
		mutations 	= new ArrayList<>();
		hybrids 	= new ArrayList<>();
		for (int i = 0; i < Const.numberOfInstance; i++) {
			State state = new State (n); Init.initRandomState (state);
			list.add (state);
		}
		numberGeneration = 1;
	}

	// calculate TSP value of all instance
	// and tsp avg value of Population
	// avgTSP = avg (top 20 tspValue State of Population)
	// easy = avg (all State);
	public void calculateTSP (int[][] a) {
		int length = this.list.size();

		// instance
		for (int i = 0; i < length; i++) {
			this.list.get(i).calculateTSP (a);
		}


	}

	public void calcAvgTSP (int[][] a) {
		int length = this.list.size();
		int sum = 0;
		Iterator iterator = this.list.iterator();

		while (iterator.hasNext()) {
			State element = (State) iterator.next();
			if (element.tspValue == -1) length --;
			else sum += element.tspValue;
		}

		// Population
		double oldAvgTSP = this.avgTSP;
		this.avgTSP      = (double) sum / length;

		if (oldAvgTSP == 0)
			this.delta = this.avgTSP;
		else
			this.delta = oldAvgTSP - this.avgTSP;
	}

	public void selectParent () {
		int size = this.list.size();
		Comparator<State> comparator = new Comparator<State> () {
			@Override
			public int compare (State left, State right) {
				return left.tspValue - right.tspValue;
			}
		};
		Collections.sort (this.list, comparator);

		// copy top 40 elements to parents
		// easy : all are top elements
		for (int i = 0; i < 70; i++) {
			this.parents.add (this.list.get(i));
		}

		// random parent
	}

	// set parent Mutation
	// add to list Mutation and remove from parents
	public void setMutation () {
		Iterator iterator = this.parents.iterator();
		while (iterator.hasNext() && Math.random() > Const.HYBRID) {
			State element = (State) iterator.next();
			this.mutations.add (new Mutation(element));
			iterator.remove();
		}
	}

	// set Hybrid couple for combine
	// from list parents after remove mutation
	public void setHybrid () {
		while (this.parents.size() > 1) {
			State element = this.parents.get(0);
			this.parents.remove (0);

			int numberHybrid = this.parents.size();

			Random rd = new Random();
			int chosen;
			if (numberHybrid == 1)
				chosen = 0;
			else
				chosen = rd.nextInt (numberHybrid - 1);

			// set new Hybrid and add to list hybrid
			// remove old hybrid
			this.hybrids.add (new Hybrid (element, this.parents.get (chosen)));
			this.parents.remove (chosen);

		}
		if (this.parents.size() == 1) {
			this.parents.remove (0);
		}

	}

	public void setGenerateMethod () {
		this.setMutation ();
		this.setHybrid ();
	}


	// generate
	public void mutate () {
		Iterator iterator = mutations.iterator();
		while (iterator.hasNext()) {
			Mutation mutation 	= (Mutation) iterator.next();
			State child 		= mutation.mutate ();
			this.list.add (child);
		}
	}

	public void combine () {
		Iterator iterator = hybrids.iterator();
		while (iterator.hasNext()) {
			Hybrid hybrid 			= (Hybrid) iterator.next();
			List<State> children 	= hybrid.combine();
			this.list.add (children.get(0));
			this.list.add (children.get(1));
		}
	}

	public void generateGeneration () {
		this.mutate ();
		this.combine ();
	}

	// Selection
	public void select () {
		// order list
		int size = this.list.size();
		Comparator<State> comparator = new Comparator<State> () {
			@Override
			public int compare (State left, State right) {
				return left.tspValue - right.tspValue;
			}
		};
		Collections.sort (this.list, comparator);

		// selection
		int numberSelect 	= 0;
		Iterator iterator 	= this.list.iterator();
		while (iterator.hasNext()) {
			if (numberSelect < Const.numberOfInstance) {
				iterator.next();
				numberSelect ++;
			} else {
				iterator.next();
				iterator.remove();
			}
		}
	}

	// generate the next generation
	public void generate (int[][] a) {
		this.selectParent ();
		this.setGenerateMethod ();
		this.generateGeneration ();
		this.calculateTSP (a);
		this.select ();
		this.calcAvgTSP (a);

		// increase number generation
		this.numberGeneration ++;
	}


	// generate next generation using DEGA
	public void generateDEGA (int[][] a) {
		int deversityValue = 0;
		mode = "Exploit"


	}

	// get result
	public State getBestState () {
		return this.list.get(0);
	}

	public void print () {
		Iterator iterator = this.list.iterator();
		while (iterator.hasNext()) {
			State element = (State) iterator.next();
			element.print ();
		}
	}
}




class Mutation {
	State instance;

	public Mutation (State state) {
		this.instance = state;
	}

	public State mutate () {
		int n       = this.instance.x.length;
		State child = new State(n);
		Random rand = new Random();
		int point2  = rand.nextInt (n);

		while (point2 < n / 2) {point2 = rand.nextInt (n);}

		int point1 = rand.nextInt (point2);

		while (point1 == 0) {point1 = rand.nextInt (point2);}

		for (int i = 0; i < n; i++) {
			child.x[i] = this.instance.x[i];
		}

		Function.reverse (child.x, point1, point2);

		return child;
	}
}



class Hybrid {
	State parent1;
	State parent2;

	public Hybrid (State state1, State state2) {
		this.parent1 = state1;
		this.parent2 = state2;
	}

	public List<State> combine () {
		int n 				= parent1.x.length;
		List<State> list 	= new ArrayList<State>();
		State child1 		= new State (n);
		State child2 		= new State (n);

		Random rand = new Random();
		int point   = rand.nextInt (n);
		while (point == 0 || point == n) {point = rand.nextInt (n);};

		for (int i = 0; i < point; i++) {
			child1.x[i] = parent1.x[i];
			child2.x[i] = parent2.x[i];
		}

		// Merge
		Function.merge (child1, child2, point, parent1, parent2);


		list.add (child1);
		list.add (child2);

		return list;
	}

}








class State {
	int[] x;
	int tspValue = 0;

	public State (int n) {
		this.x = new int[n];
	}

	public void calculateTSP (int[][] a) {
		int tspValue = 0;
		int n = this.x.length;

		for (int i = 0; i < n - 1; i++) {
		    if (a[x[i]][x[i+1]] == -1) {
		        tspValue = -1;
		        break;
		    }
			tspValue += a[x[i]][x[i+1]];
		}
		if (tspValue == -1);
		else tspValue += a[x[n-1]][x[0]];

		this.tspValue = tspValue;
	}

	public static void updateResult (State result, State newState) {
		if (result.tspValue > newState.tspValue) {
			int n = result.x.length;

			for (int i = 0; i < n; i++) result.x[i] = newState.x[i];

			result.tspValue = newState.tspValue;
		}
	}

	// for loop all State
	public boolean nextState () {
		int n = this.x.length;
		int i = n - 1;

		while (i > 0 && this.x[i-1] > this.x[i]) {i--;}

		if (i <= 0) return false;

		i     = i - 1;
		int j = Function.findMinPosition(this.x, i);

		Function.swap (this.x, i, j);
		Function.reverse (this.x, i + 1);

		return true;
	}


	// Print Result
	public void print () {
		System.out.print ("State : ");
		int n = x.length;

		for (int i = 0; i < n; i++) {
			System.out.print (" " + x[i]);
		}

		System.out.println();
		System.out.println ("TSP Value : " + this.tspValue);
	}


}


// fundamental function
class Function {
	public static void swap (int[] a, int i, int j) {
		int temp = a[i];
		a[i]     = a[j];
		a[j]     = temp;
	}

	// return value of position have value minimum greater than min
	// start: positon start
	public static int findMinPosition (int[] a, int start) {
		int n      = a.length;
		int minset = a[start];
		int min    = n;
		int jmin   = start + 1;

		for (int j = start + 1; j < n; j++) {
			if (a[j] < min && a[j] > minset) {jmin = j; min = a[j];}
		}
		return jmin;
	}

	public static void reverse (int[] a, int start) {
		int j = a.length - 1;

		int i = start;
		while (i < j) {
			Function.swap (a, i, j);
			i++; j--;
		}
	}

	public static void reverse (int[] a, int start, int end) {
		int i = start;
		int j = end;
		while (i < j) {
			Function.swap(a, i, j);
			i++; j--;
		}
	}

	// terminate condition:
	// delta < Constant.delta
	public static boolean checkTerminate (Population population) {
		if (population.delta < Const.DELTA
		    && population.numberGeneration > Const. numberGeneration)
			return false;
		return true;
	}

	public static void merge (State child1, State child2, int point, State parent1, State parent2) {

		Function.mergePart (child1, point, parent1, parent2);
		Function.mergePart (child2, point, parent2, parent1);
	}

	public static void mergePart (State child1, int point, State parent1, State parent2) {
		int n = parent1.x.length;
		int i = point;
		int j = point;

		// put element to array if not duplicate
		for (;j < n; j++) {
			if (!Function.checkExistHead (parent2.x[j], child1.x, point)) {
				child1.x[i] = parent2.x[j];
				i++;
			}
		}

		// add missing number
		// check value of element after point
		// of 2 array
		// if each element in 1 doesn't appear in 2
		// add it to array
		for (int k = point; k < n; k++) {
			if (!checkExistEnd (parent1.x[k], parent2.x, point)) {
				child1.x[i] = parent1.x[k]; i++;
			}
		}


	}

	// check exist
	public static boolean checkExistHead (int check, int[] arr, int point) {
		for (int i = 0; i < point; i++) {
			if (arr[i] == check)
				return true;
		}
		return false;
	}

	public static boolean checkExistEnd (int check, int[] arr, int point) {
		int n = arr.length;
		for (int i = point; i < n; i++) {
			if (arr[i] == check)
				return true;
		}
		return false;
	}

	// function to calculate Diversity value
	public static float calcDiversity (Population p) {
		// length of the diagonal of the search space
		int l              = Math.sqrt(n * n + n * n);
		int populationSize = p.list.size;
		int n              = 2;

		// average of TSP population
		State avgState = Function.findAvgVAlue (p);

		// calc diversity value
		float result;

		// calculate sum
		float sum;

		Iterator iterator = p.list.iterator();

		while (iterator.hasNext()) {

			// loop each instance to update Bang Xac Suat
			State instance = (State) iterator.next();

			// sum of each instance
			int sumChild = 0;
			for (int i = 0; i < n - 1; i++) {
				sumChild += Math.pow((instance.x[i] - avgState.x[i]), 2);
			}

			sum += Math.sqrt(sumChild);
		} // end loop Population

		result = (1.0/(l * populationSize)) * sum;

		return result;
	}

	// function to predict average State
	// based on xac suat xuat hien cua gen tai moi diem trong doan gen
	public static State findAvgValue (Population p) {
		// willing to set this to Constants
		int n = 10;

		int bangXacSuat[n][n];

		// loop all Population to get Bang Xac suat xuat hien cua cac gen

		// loop each instance of Population
		Iterator iterator = p.list.iterator();

		while (iterator.hasNext()) {

			// loop each instance to update Bang Xac Suat
			State instance = (State) iterator.next();
			for (int i = 0; i < n - 1; i++) {
				int value = instance.x[i];
				// update Bang Xac suat
				bangXacSuat[value][i]++;
			}
		} // end loop Population

		// get avgInstance from bang Xac suat


		// State to keep result
		State avgState = new State();

		// get max value of bang xac suat
		// take that value and remove that col from next check
		boolean[] check = new boolean[n];

		do {
			// check array
			// check[i] = true: col i is used

			int[] result = getMaxValueTable(bangXacSuat, check);
			check[result[1]] = true;
			avgState.x[result[1]] = result[2];

			// set apperance of max value to 0
			// all row of that max value set to 0 in bangXacSuat
			for (i = 0; i < n; i++ ) {
				bangXacSuat[result[2]][i] = 0;
			}

			// check Break Condition
			// break if all col is check
			boolean breakCondition = true;

			for (int i = 0; i < n; i++) {
				if (!check[i])
					breakCondition = false;
			}

			if (breakCondition)
				break;
		} while (true);

		return avgState;
	} // end func findAvgValue

	public static int[] getMaxValueTable (int[][] table, boolean[] check) {
		int[] result = new int[3];

		int maxValue = table[0][0];
		int imax     = 0;
		int jmax     = 0;

		for (int i = 0; i < n - 1; i ++) {
			for (int j = 0; j < n - 1; j ++) {
				// if col is already used
				if (check[j]) continue;

				if (table[i][j] > maxValue) {
					maxValue = table[i][j];
					imax     = i;
					jmax     = j;
				}
			}
		}

		result[0] = imax;
		result[1] = jmax;
		result[2] = maxValue;

		return result;
	} // end func getMaxValTable
}


// Const of program

class Const {
	static final int numberOfInstance = 100;
	static final double HYBRID        = 0.2;
	static final double DELTA         = 0.0001;
	static final int numberGeneration = 10000;
}
