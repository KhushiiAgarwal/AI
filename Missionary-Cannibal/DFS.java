import java.util.*;

class State {
    int missionariesLeft, cannibalsLeft, missionariesRight, cannibalsRight;
    boolean boat; /
    State parent;

    State(int ml, int cl, int mr, int cr, boolean b) {
        missionariesLeft = ml;
        cannibalsLeft = cl;
        missionariesRight = mr;
        cannibalsRight = cr;
        boat = b;
    }

    boolean isValid() {
        if (missionariesLeft < 0 || missionariesRight < 0 || cannibalsLeft < 0 || cannibalsRight < 0  || (missionariesLeft != 0 && missionariesLeft < cannibalsLeft)  || (missionariesRight != 0 && missionariesRight < cannibalsRight))
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

public class DFS {
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

    static boolean dfs(State initial, HashSet<String> visited) {
        Stack<State> stack = new Stack<>();
        stack.push(initial);

        while (!stack.isEmpty()) {
            State current = stack.pop();

            if (current.isGoal()) {
                System.out.println("\nGoal State:");
                current.print();
                System.out.println("\nPath from initial to goal:");
                printPath(current);
                return true;
            }

            visited.add(current.missionariesLeft + " " + current.cannibalsLeft + " " + current.boat + " "  + current.missionariesRight + " " + current.cannibalsRight);

            int m[] = { 1, 0, 2, 0, 1 }, c[] = { 0, 1, 0, 2, 1 };

            for (int i = 0; i < 5; i++) {
                if (current.boat) {
                    State nextState = new State(current.missionariesLeft - m[i], current.cannibalsLeft - c[i], current.missionariesRight + m[i], current.cannibalsRight + c[i], false);
                    if (nextState.isValid() && !visited.contains(nextState.missionariesLeft + " " + nextState.cannibalsLeft + " " + nextState.boat + " " + nextState.missionariesRight + " " + nextState.cannibalsRight))
                        stack.push(nextState);
                } else {
                    State nextState = new State(current.missionariesLeft + m[i], current.cannibalsLeft + c[i],   current.missionariesRight - m[i], current.cannibalsRight - c[i], true);
                    if (nextState.isValid() && !visited.contains(nextState.missionariesLeft + " " + nextState.cannibalsLeft + " " + nextState.boat + " " + nextState.missionariesRight + " "                            + nextState.cannibalsRight))
                        stack.push(nextState);
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        State initial = new State(3, 3, 0, 0, true);
        System.out.println("Initial State:");
        initial.print();
        System.out.println("\nIntermediate States:");
        boolean solutionExists = dfs(initial, new HashSet<>());
        if (!solutionExists) {
            System.out.println("No solution exists.");
        }
    }
}

