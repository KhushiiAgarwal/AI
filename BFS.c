void dfs(int root, int n) {
    int stack[MAX_NODES], top = -1;
    int i, curr;

    // Push the root node onto the stack and mark it as visited
    stack[++top] = root;
    visited[root] = 1;

    while (top != -1) {
        // Pop the next node off the stack
        curr = stack[top--];
        printf("%d ", curr);

        // Push all unvisited neighbors onto the stack
        for (i = n - 1; i >= 0; i--) {
            if (adjMatrix[curr][i] && !visited[i]) {
                stack[++top] = i;
                visited[i] = 1;
            }
        }
    }
}
