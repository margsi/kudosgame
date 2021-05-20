import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

fun main(args: Array<String>) {

    val json = File(System.getProperty("user.dir") + "\\resources\\" + "kudos.json").readText()
    var kudos = Gson().fromJson<List<Kudos>>(json)

    val groupedBySender = kudos.groupBy { k -> k.sender_id }.toMap()

    val g = Graph(kudos.size)

    kudos.forEach { k ->
        if (k.person_id == null) {
            kudos.forEach { other ->
                if(other.id != k.id) {
                    g.addEdge((k.id - 1).toInt() , (other.id - 1).toInt(), 1)
                }
            }
        } else {
            groupedBySender[k.person_id]?.forEach { targetKudoPair ->
                g.addEdge((k.id - 1).toInt(), (targetKudoPair.id - 1).toInt(), 10)
            }
        }
    }
    val path = g.longestPath(0)
    val kudoPath = path.map { kudos[it].id + 1 }
    print(path)
    print(kudoPath)
}

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)
