package kg.task.dreamfield.dto.http.word

data class GuessWordDto(
        val description: String,
        val maxScores: Int,
        val availableScore: Int
)