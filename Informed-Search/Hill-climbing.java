import java.util.*;

class State {
    int missionariesLeft, cannibalsLeft, missionariesRight, cannibalsRight;
    boolean boat; 
    State parent;

    State(int ml, int cl, int mr, int cr, boolean b) {
        missionariesLeft = ml;
        cannibalsLeft = cl;
        missionariesRight = mr;
        cannibalsRight = cr;
        boat = b;
    }

    boolean isValid() {
        if (missionariesLeft < 0 || missionariesRight < 0 || cannibalsLeft < 0 || cannibalsRight < 0 || (missionariesLeft != 0 && missionariesLeft < cannibalsLeft) || (missionariesRight != 0 && missionariesRight < cannibalsRight))
            return false;
        return true;
    }

    boolean isGoal() {
        return missionariesLeft == 0 && cannibalsLeft == 0;
    }

    void print() {
        System.out.println("(" + missionariesLeft + ", " + cannibalsLeft + ", " + missionariesRight + ", " + cannibalsRight + ", " + (boat ? "left" : "right") + ")");
    }
}

public class Main {
    static void printPath(State state) {
        List<State> path = new ArrayList<>();
        while (state != null) {
            path.add(state);
            state = state.parent;
        }
        Collections.reverse(path);
        for (State s : path) {
            s.print();
        }
    }

    static boolean hillClimbing(State initial, int n, HashSet<String> visited) {
        if (n <= 0) {
            return false;
        }

        if (initial.isGoal()) {
            System.out.println("\nGoal State:");
            initial.print();
            System.out.println("\nPath from initial to goal:");
            printPath(initial);
            return true;
        }
        
        initial.print(); // Print initial state
        
        visited.add(initial.missionariesLeft + " " + initial.cannibalsLeft + " " + initial.boat + " " + initial.missionariesRight + " " + initial.cannibalsRight);
        int m[] = { 1, 0, 2, 0, 1 }, c[] = { 0, 1, 0, 2, 1 };
        boolean found = false;

        for (int i = 0; i < 5; i++) {
            if (initial.boat) {
                State nextState = new State(initial.missionariesLeft - m[i], initial.cannibalsLeft - c[i], initial.missionariesRight + m[i], initial.cannibalsRight + c[i], false);
                if (nextState.isValid() && !visited.contains(nextState.missionariesLeft + " " + nextState.cannibalsLeft + " " + nextState.boat + " " + nextState.missionariesRight + " " + nextState.cannibalsRight))
                    found = hillClimbing(nextState, n - 1, visited);
            } else {
                State nextState = new State(initial.missionariesLeft + m[i], initial.cannibalsLeft + c[i], initial.missionariesRight - m[i], initial.cannibalsRight - c[i], true);
                if (nextState.isValid() && !visited.contains(nextState.missionariesLeft + " " + nextState.cannibalsLeft + " " + nextState.boat + " " + nextState.missionariesRight + " " + nextState.cannibalsRight))
                    found = hillClimbing(nextState, n - 1, visited);
            }
            if (found) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        State initial = new State(3, 3, 0, 0, true);
        System.out.println("Initial State:");
        initial.print();

        System.out.print("Enter the value of N for hill climbing: ");
        int n = scanner.nextInt();
        //int n = 15;
        System.out.println("\nIntermediate States:");

        boolean solutionExists = false;
        HashSet<String> visited = new HashSet<>();
        solutionExists = hillClimbing(initial, n, visited);

        if (!solutionExists) {
            System.out.println("No solution exists within the Nth hill climbing limit.");
        }
    }
}
