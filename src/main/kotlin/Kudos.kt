import java.time.LocalDateTime

class Kudos(
    val id: Long,
    val sender_id: Long?,
    val creation_date: String,
    val content: String,
    val organization_unit_id: Long?,
    val person_id: Long?,
    val checked: Boolean
) {
}
