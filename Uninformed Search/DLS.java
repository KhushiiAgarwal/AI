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

    static boolean dls(State initial, int depth, HashSet<String> visited) {
        if (depth < 0) {
            return false;
        }

        if (initial.isGoal()) {
            System.out.println("\nGoal State:");
            initial.print();
            System.out.println("\nPath from initial to goal:");
            printPath(initial);
            return true;
        }

        visited.add(initial.missionariesLeft + " " + initial.cannibalsLeft + " " + initial.boat + " " + initial.missionariesRight + " " + initial.cannibalsRight);
        int m[] = { 1, 0, 2, 0, 1 }, c[] = { 0, 1, 0, 2, 1 };
        boolean found = false;
        for (int i = 0; i < 5; i++) {
            State nextState;
            if (initial.boat) {
                nextState = new State(initial.missionariesLeft - m[i], initial.cannibalsLeft - c[i], initial.missionariesRight + m[i], initial.cannibalsRight + c[i], false);
            } else {
                nextState = new State(initial.missionariesLeft + m[i], initial.cannibalsLeft + c[i], initial.missionariesRight - m[i], initial.cannibalsRight - c[i], true);
            }

            if (nextState.isValid() && !visited.contains(nextState.missionariesLeft + " " + nextState.cannibalsLeft + " " + nextState.boat + " " + nextState.missionariesRight + " " + nextState.cannibalsRight)) {
                nextState.parent = initial;
                found = dls(nextState, depth - 1, visited);
                if (found) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        State initial = new State(3, 3, 0, 0, true);
        System.out.println("Initial State:");
        initial.print();

        System.out.print("Enter the maximum depth limit: ");
        int maxDepth = scanner.nextInt();
        System.out.println("\nIntermediate States:");

        boolean solutionExists = false;
        HashSet<String> visited = new HashSet<>();
        for (int depth = 0; depth <= maxDepth; depth++) {
            visited.clear();
            solutionExists = dls(initial, depth, visited);
            if (solutionExists) {
                break;
            }
        }

        if (!solutionExists) {
            System.out.println("No solution exists within the depth limit.");
        }
    }
}
