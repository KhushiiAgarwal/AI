#include <stdio.h>
#include <stdbool.h>

#define V 7 // Number of vertices in the graph

// Graph representation using adjacency matrix
int graph[V][V] = {
    {0, 1, 1, 0, 0, 0, 0},
    {0, 0, 0, 1, 1, 0, 0},
    {0, 0, 0, 0, 0, 1, 1},
    {0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0}
};

bool visited[V];

void dfs(int vertex, int depthLimit, int currentDepth) {
    if (currentDepth > depthLimit) {
        return;
    }

    printf("%d ", vertex);
    visited[vertex] = true;

    for (int i = 0; i < V; i++) {
        if (graph[vertex][i] == 1 && !visited[i]) {
            dfs(i, depthLimit, currentDepth + 1);
        }
    }
}

int main() {
    int startVertex, depthLimit;

    printf("Enter the starting vertex: ");
    scanf("%d", &startVertex);

    printf("Enter the depth limit: ");
    scanf("%d", &depthLimit);

    printf("Depth-Limited DFS starting from vertex %d with depth limit %d:\n", startVertex, depthLimit);

    // Initialize visited array
    for (int i = 0; i < V; i++) {
        visited[i] = false;
    }

    dfs(startVertex, depthLimit, 0);

    return 0;
}
