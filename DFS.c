#include<stdio.h>
#include<stdlib.h>

// Data structure to represent a graph node
struct Node {
    int data;
    struct Node* next;
};

// Data structure to represent a graph
struct Graph {
    int V;
    struct Node** adjList;
};

// Function to create a new graph node
struct Node* createNode(int data) {
    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
    newNode->data = data;
    newNode->next = NULL;
    return newNode;
}

// Function to create a new graph
struct Graph* createGraph(int V) {
    struct Graph* graph = (struct Graph*)malloc(sizeof(struct Graph));
    graph->V = V;
    graph->adjList = (struct Node**)malloc(V * sizeof(struct Node*));
    for (int i = 0; i < V; ++i)
        graph->adjList[i] = NULL;
    return graph;
}

// Function to add an edge to the graph
void addEdge(struct Graph* graph, int v, int w) {
    struct Node* newNode = createNode(w);
    newNode->next = graph->adjList[v];
    graph->adjList[v] = newNode;
}

// Function to perform DFS traversal starting from vertex v
void DFSUtil(struct Graph* graph, int v, int visited[]) {
    // Mark the current node as visited and print it
    visited[v] = 1;
    printf("%d ", v);

    // Recur for all the vertices adjacent to this vertex
    struct Node* temp = graph->adjList[v];
    while (temp != NULL) {
        int n = temp->data;
        if (!visited[n])
            DFSUtil(graph, n, visited);
        temp = temp->next;
    }
}

// Function to perform DFS traversal on the graph
void DFS(struct Graph* graph, int v) {
    // Mark all the vertices as not visited
    int* visited = (int*)malloc(graph->V * sizeof(int));
    for (int i = 0; i < graph->V; ++i)
        visited[i] = 0;

    // Call the recursive helper function to print DFS traversal
    DFSUtil(graph, v, visited);

    free(visited);
}

// Driver Code
int main() {
    struct Graph* g = createGraph(4);

    addEdge(g, 0, 1);
    addEdge(g, 0, 2);
    addEdge(g, 1, 2);
    addEdge(g, 2, 0);
    addEdge(g, 2, 3);
    addEdge(g, 3, 3);

    printf("Following is Depth First Traversal\n");

    // Function call
    DFS(g, 0);

    return 0;
}
