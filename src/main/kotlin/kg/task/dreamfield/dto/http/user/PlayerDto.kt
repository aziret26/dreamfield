package kg.task.dreamfield.dto.http.user

data class PlayerDto(
        val id: Long,
        val name: String,
        val email: String,
        val score: Long
)