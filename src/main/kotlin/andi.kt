import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import kotlin.random.Random

data class Node(val next: Long, var used: Boolean = false)

fun main(args: Array<String>) {

    val json = File(System.getProperty("user.dir") + "\\resources\\" + "kudos.json").readText()
    var kudos = Gson().fromJson<List<Kudos>>(json)

    val groupedBySender = kudos.groupBy { k -> k.sender_id }.toMap()

    val g = Graph(kudos.size)

    val arrows = (kudos.filter { it.person_id != null}.mapNotNull { k ->
        groupedBySender[k.person_id]?.map { targetKudoPair ->
            k.id to targetKudoPair.id
        }
    }.flatMap { it }
            +
        kudos.filter { it.person_id == null}.flatMap { k ->
                kudos.filter { other -> other.id != k.id }.map { other -> k.id to other.id }
        }
    )
    .groupBy { it.first }.mapValues { it.value.map { target -> Node(target.second, false) } }

    val nodes = mutableListOf<Long>()
    var i: Long? = Random.nextLong(kudos.last().id)
    while (i != null) {
        nodes.add(i)
        i = arrows[i]?.filter { !it.used }?.firstOrNull()?.let {
            it.used = true
            it.next
        }
    }

    print(nodes)
}

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)
