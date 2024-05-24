import java.util.*;

class State {
    int x, y;
    State parent;

    public State(int x, int y, State parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

public class Astar {
    static final int MAX_X = 4; 
    static final int MAX_Y = 3; 
    static final int GOAL = 2; 

    static void solve() {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(Main::heuristic));
        Set<State> visited = new HashSet<>();

        queue.offer(new State(0, 0, null));

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.x == GOAL || currentState.y == GOAL) {
                printPath(currentState);
                return;
            }

            visited.add(currentState);

            if (currentState.x > 0) {
                State newState = new State(0, currentState.y, currentState);
                if (!visited.contains(newState)) {
                    queue.offer(newState);
                }
            }

            if (currentState.y > 0) {
                State newState = new State(currentState.x, 0, currentState);
                if (!visited.contains(newState)) {
                    queue.offer(newState);
                }
            }

            if (currentState.x < MAX_X) {
                State newState = new State(MAX_X, currentState.y, currentState);
                if (!visited.contains(newState)) {
                    queue.offer(newState);
                }
            }

            if (currentState.y < MAX_Y) {
                State newState = new State(currentState.x, MAX_Y, currentState);
                if (!visited.contains(newState)) {
                    queue.offer(newState);
                }
            }

            if (currentState.x > 0 && currentState.y < MAX_Y) {
                int amountToPour = Math.min(currentState.x, MAX_Y - currentState.y);
                State newState = new State(currentState.x - amountToPour, currentState.y + amountToPour, currentState);
                if (!visited.contains(newState)) {
                    queue.offer(newState);
                }
            }

            if (currentState.y > 0 && currentState.x < MAX_X) {
                int amountToPour = Math.min(currentState.y, MAX_X - currentState.x);
                State newState = new State(currentState.x + amountToPour, currentState.y - amountToPour, currentState);
                if (!visited.contains(newState)) {
                    queue.offer(newState);
                }
            }
        }

        System.out.println("No solution found!");
    }

    static int heuristic(State state) {
        return Math.abs(state.x - GOAL) + Math.abs(state.y - GOAL);
    }

    static void printPath(State state) {
        List<State> path = new ArrayList<>();
        while (state != null) {
            path.add(state);
            state = state.parent;
        }
        Collections.reverse(path);
        for (State s : path) {
            System.out.print(s + " -> ");
        }
        System.out.println("Goal");
    }

    public static void main(String[] args) {
        solve();
    }
}
