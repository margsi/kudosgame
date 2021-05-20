import com.google.gson.Gson
import java.io.File

fun main(args: Array<String>) {
    val json = File(System.getProperty("user.dir") + "\\resources\\" + "kudos.json").readText()
    var kudos = Gson().fromJson<List<Kudos>>(json)
    var highScore = 0
    (1L..100L).forEach {
        val path = findPath(kudos,it)
        if(path.size > highScore){
            println(path.joinToString())
        }
    }
}

fun findPath(kudosList: List<Kudos>, startId: Long): List<Long> {
    val kudos = kudosList.toMutableList()
    val path = mutableListOf<Long>(startId)
    var currentKudo = kudos.find { it.id == startId }
    kudos.remove(currentKudo)
    var possibleNextSteps = kudos.filter { it.sender_id == currentKudo!!.person_id }
    while (possibleNextSteps.isNotEmpty()) {
        currentKudo = possibleNextSteps[0]
        path.add(currentKudo.id)
        kudos.remove(currentKudo)
        possibleNextSteps = kudos.filter { it.sender_id == currentKudo!!.person_id }
    }
    return path
}
