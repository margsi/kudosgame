import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

fun main(args: Array<String>) {

    val json =File(System.getProperty("user.dir") + "\\resources\\" + "kudos.json").readText()
    val kudos = Gson().fromJson<List<Kudos>>(json)
    println(kudos)
}

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)
