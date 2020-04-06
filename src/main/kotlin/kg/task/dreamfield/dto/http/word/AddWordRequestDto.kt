package kg.task.dreamfield.dto.http.word

data class AddWordRequestDto(
        val value: String,
        val description: String,
        val maxScores: Int
)