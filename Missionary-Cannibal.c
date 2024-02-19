
#include <stdio.h>
#include <stdlib.h>

// Struct to represent a state in the problem
struct State {
    int cannibalsLeft, missionariesLeft; // Left bank
    int cannibalsRight, missionariesRight; // Right bank
    char boat; // 'L' or 'R' for left or right bank
    struct State* parent; // Parent state
};

// Queue node for BFS
struct QueueNode {
    struct State* state;
    struct QueueNode* next;
};

// Function to create a new state
struct State* createState(int cannibalsLeft, int missionariesLeft, int cannibalsRight, int missionariesRight, char boat) {
    struct State* newState = (struct State*)malloc(sizeof(struct State));
    newState->cannibalsLeft = cannibalsLeft;
    newState->missionariesLeft = missionariesLeft;
    newState->cannibalsRight = cannibalsRight;
    newState->missionariesRight = missionariesRight;
    newState->boat = boat;
    newState->parent = NULL;
    return newState;
}

// Function to check if a state is valid
int isValidState(int cannibals, int missionaries) {
    if (missionaries > 0 && missionaries < cannibals) // Check if missionaries outnumbered by cannibals
        return 0;
    return 1;
}

// Function to check if a state is the goal state
int isGoalState(struct State* state) {
    return state->cannibalsLeft == 0 && state->missionariesLeft == 0 &&
           state->cannibalsRight == 3 && state->missionariesRight == 3 &&
           state->boat == 'R';
}

// Function to print the path from initial to final state
void printPath(struct State* state) {
    if (state == NULL)
        return;
    printPath(state->parent);
    printf("(%d, %d, %c) -> ", state->cannibalsLeft, state->missionariesLeft, state->boat);
}

// Function to enqueue a state in the BFS queue
void enqueue(struct QueueNode** front, struct QueueNode** rear, struct State* state) {
    struct QueueNode* newNode = (struct QueueNode*)malloc(sizeof(struct QueueNode));
    newNode->state = state;
    newNode->next = NULL;
    if (*front == NULL) {
        *front = *rear = newNode;
    } else {
        (*rear)->next = newNode;
        *rear = newNode;
    }
}

// Function to dequeue a state from the BFS queue
struct State* dequeue(struct QueueNode** front) {
    if (*front == NULL)
        return NULL;
    struct QueueNode* temp = *front;
    struct State* state = temp->state;
    *front = (*front)->next;
    free(temp);
    return state;
}

// Function to perform BFS to solve the problem
void bfs() {
    struct State* initialState = createState(3, 3, 0, 0, 'L'); // Initial state
    struct QueueNode* front = NULL;
    struct QueueNode* rear = NULL;
    enqueue(&front, &rear, initialState); // Enqueue initial state

    while (front != NULL) {
        struct State* currentState = dequeue(&front);
        if (isGoalState(currentState)) {
            printf("Solution found: ");
            printPath(currentState);
            printf("\n");
            return;
        }

        // Generate successor states and enqueue them
        if (currentState->boat == 'L') {
            for (int c = 0; c <= 2; c++) {
                for (int m = 0; m <= 2; m++) {
                    if (c + m > 0 && c + m <= 2) {
                        struct State* newState = createState(currentState->cannibalsLeft - c, currentState->missionariesLeft - m,
                                                              currentState->cannibalsRight + c, currentState->missionariesRight + m, 'R');
                        if (isValidState(newState->cannibalsLeft, newState->missionariesLeft) &&
                            isValidState(newState->cannibalsRight, newState->missionariesRight)) {
                            newState->parent = currentState;
                            enqueue(&front, &rear, newState);
                        }
                    }
                }
            }
        } else {
            for (int c = 0; c <= 2; c++) {
                for (int m = 0; m <= 2; m++) {
                    if (c + m > 0 && c + m <= 2) {
                        struct State* newState = createState(currentState->cannibalsLeft + c, currentState->missionariesLeft + m,
                                                              currentState->cannibalsRight - c, currentState->missionariesRight - m, 'L');
                        if (isValidState(newState->cannibalsLeft, newState->missionariesLeft) &&
                            isValidState(newState->cannibalsRight, newState->missionariesRight)) {
                            newState->parent = currentState;
                            enqueue(&front, &rear, newState);
                        }
                    }
                }
            }
        }
    }

    printf("No solution found!\n");
}

int main() {
    bfs();
    return 0;
}
