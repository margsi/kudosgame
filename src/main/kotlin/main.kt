import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

fun main(args: Array<String>) {

    val json = File(System.getProperty("user.dir") + "\\resources\\" + "kudos.json").readText()
    var kudos = Gson().fromJson<List<Kudos>>(json)
    kudos = kudos.filter { it.person_id != null }

//    val personIndex = kudos.mapIndexed { i, k -> k.person_id to i }.toMap()
    val groupedBySender = kudos.mapIndexed { i, k -> Pair(i, k) }.groupBy { k -> k.second.sender_id }.toMap()

    val g = Graph(kudos.size)

    kudos.forEachIndexed { graphStart, k ->
//        if (k.person_id == null) {
//            return
//        }
        groupedBySender[k.person_id]?.forEach { targetKudoPair ->
            g.addEdge(graphStart, targetKudoPair.first, 1)

        }
    }
    val path = g.longestPath(0)
    print(path)
}

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)
