import java.util.*


fun main(args: Array<String>) {
    // Create a graph given in the above diagram.
    // Here vertex numbers are 0, 1, 2, 3, 4, 5 with
    // following mappings:
    // 0=r, 1=s, 2=t, 3=x, 4=y, 5=z
    val g = Graph(6)
    g.addEdge(0, 1, 5)
    g.addEdge(0, 2, 3)
    g.addEdge(1, 3, 6)
    g.addEdge(1, 2, 2)
    g.addEdge(2, 4, 4)
    g.addEdge(2, 5, 2)
    g.addEdge(2, 3, 7)
    g.addEdge(3, 5, 1)
    g.addEdge(3, 4, -1)
    g.addEdge(4, 5, -2)
    val s = 1
    print("Following are longest distances from source vertex $s \n")

    val chain = g.longestPath(s)
    val chainStr = chain.joinToString(",", prefix = "[", postfix = "]")
    print(chainStr)
}

class AdjListNode(var v: Int, var weight: Int)

// Class to represent a graph using adjacency list
// representation
class Graph(  // No. of vertices'
    var V: Int
) {
    // Pointer to an array containing adjacency lists
    var adj: ArrayList<ArrayList<AdjListNode>>
    fun addEdge(u: Int, v: Int, weight: Int) {
        val node = AdjListNode(v, weight)
        adj[u].add(node) // Add v to u's list
    }

    // A recursive function used by longestPath. See below
    // link for details
    // https:// www.geeksforgeeks.org/topological-sorting/
    fun topologicalSortUtil(
        v: Int, visited: BooleanArray,
        stack: Stack<Int>
    ) {
        // Mark the current node as visited
        visited[v] = true

        // Recur for all the vertices adjacent to this vertex
        for (i in adj[v].indices) {
            val node = adj[v][i]
            if (!visited[node.v]) topologicalSortUtil(node.v, visited, stack)
        }

        // Push current vertex to stack which stores topological
        // sort
        stack.push(v)
    }

    // The function to find longest distances from a given vertex.
    // It uses recursive topologicalSortUtil() to get topological
    // sorting.
    fun longestPath(s: Int): List<Int> {
        val stack = Stack<Int>()
        val dist = IntArray(V)

        // Mark all the vertices as not visited
        val visited = BooleanArray(V)
        for (i in 0 until V) visited[i] = false

        // Call the recursive helper function to store Topological
        // Sort starting from all vertices one by one
        for (i in 0 until V) if (visited[i] == false) topologicalSortUtil(i, visited, stack)

        // Initialize distances to all vertices as infinite and
        // distance to source as 0
        for (i in 0 until V) dist[i] = Int.MIN_VALUE
        dist[s] = 0

        // Process vertices in topological order
        while (stack.isEmpty() == false) {

            // Get the next vertex from topological order
            val u = stack.peek()
            stack.pop()

            // Update distances of all adjacent vertices ;
            if (dist[u] != Int.MIN_VALUE) {
                for (i in adj[u].indices) {
                    val node = adj[u][i]
                    if (dist[node.v] < dist[u] + node.weight) dist[node.v] = dist[u] + node.weight
                }
            }
        }

        // Print the calculated longest distances
        return dist.toList().subList(1, dist.lastIndex)

//        for (i in 0 until V) if (dist[i] == Int.MIN_VALUE) print("INF ") else print(dist[i].toString() + " ")
    }

    init  // Constructor
    {
        adj = ArrayList(V)
        for (i in 0 until V) {
            adj.add(ArrayList())
        }
    }
}
