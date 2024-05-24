import java.util.*;

class State {
    int cannibalsLeft, missionariesLeft, cannibalsRight, missionariesRight;
    char boat; // 'L' for left, 'R' for right
    State parent;

    State(int cl, int ml, int cr, int mr, char b) {
        this.cannibalsLeft = cl;
        this.missionariesLeft = ml;
        this.cannibalsRight = cr;
        this.missionariesRight = mr;
        this.boat = b;
        this.parent = null;
    }

    boolean isValidState() {
        if (missionariesLeft >= 0 && cannibalsLeft >= 0 && missionariesRight >= 0 && cannibalsRight >= 0 &&
            (missionariesLeft == 0 || missionariesLeft >= cannibalsLeft) &&
            (missionariesRight == 0 || missionariesRight >= cannibalsRight)) {
            return true;
        }
        return false;
    }

    boolean isGoalState() {
        return cannibalsLeft == 0 && missionariesLeft == 0 &&
               cannibalsRight == 3 && missionariesRight == 3 &&
               boat == 'R';
    }

    void printPath() {
        if (this.parent != null) {
            this.parent.printPath();
        }
        System.out.printf("(%d, %d, %d, %d, %c) -> ", cannibalsLeft, missionariesLeft, cannibalsRight, missionariesRight, boat);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State state = (State) obj;
        return cannibalsLeft == state.cannibalsLeft && missionariesLeft == state.missionariesLeft &&
               cannibalsRight == state.cannibalsRight && missionariesRight == state.missionariesRight &&
               boat == state.boat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cannibalsLeft, missionariesLeft, cannibalsRight, missionariesRight, boat);
    }
}

public class Main {
    static void bfs() {
        State initialState = new State(3, 3, 0, 0, 'L'); 
        System.out.printf("Initial State: (%d, %d, %d, %d, %c)\n", initialState.cannibalsLeft, initialState.missionariesLeft, initialState.cannibalsRight, initialState.missionariesRight, initialState.boat);

        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        queue.add(initialState); 
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.isGoalState()) {
                System.out.println("Solution found: ");
                currentState.printPath();
                System.out.println("Goal");
                return;
            }

            for (int c = 0; c <= 2; c++) {
                for (int m = 0; m <= 2; m++) {
                    if (c + m > 0 && c + m <= 2) {
                        State newState;
                        if (currentState.boat == 'L') {
                            newState = new State(currentState.cannibalsLeft - c, currentState.missionariesLeft - m,
                                                 currentState.cannibalsRight + c, currentState.missionariesRight + m, 'R');
                        } else {
                            newState = new State(currentState.cannibalsLeft + c, currentState.missionariesLeft + m,
                                                 currentState.cannibalsRight - c, currentState.missionariesRight - m, 'L');
                        }

                        if (newState.isValidState() && !visited.contains(newState)) {
                            newState.parent = currentState;
                            queue.add(newState);
                            visited.add(newState);
                        }
                    }
                }
            }
        }

        System.out.println("No solution found!");
    }

    public static void main(String[] args) {
        bfs();
    }
}
