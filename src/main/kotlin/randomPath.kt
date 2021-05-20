import com.google.gson.Gson
import java.io.File
import kotlin.random.Random

fun main(args: Array<String>) {
    val json = File(System.getProperty("user.dir") + "\\resources\\" + "kudos.json").readText()
    var kudos = Gson().fromJson<List<Kudos>>(json)
    var highScore = 0
    (kudos.indices).forEach {startIndex ->
        (0..10000).forEach {
            val path = findPath(kudos,startIndex)
            if(path.size > highScore){
                println("Path with size ${path.size}:")
                println(path.joinToString())
                highScore = path.size
            }
        }
    }
}

fun findPath(kudosList: List<Kudos>, startIndex: Int): List<Long> {
    val kudos = kudosList.toMutableList()
    var currentKudo = kudos[startIndex]
    val path = mutableListOf<Long>(currentKudo.id)
    kudos.remove(currentKudo)
    var possibleNextSteps = kudos.filter { it.sender_id == currentKudo!!.person_id }
    while (possibleNextSteps.isNotEmpty()) {
        val randomNextIndex = Random.nextInt(possibleNextSteps.size)
        currentKudo = possibleNextSteps[randomNextIndex]
        path.add(currentKudo.id)
        kudos.remove(currentKudo)
        if(currentKudo.person_id!= null) {
            possibleNextSteps = kudos.filter { it.sender_id == currentKudo!!.person_id }
        } else {
            possibleNextSteps = kudos
        }
    }
    return path
}
