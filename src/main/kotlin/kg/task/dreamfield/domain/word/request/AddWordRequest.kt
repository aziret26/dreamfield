package kg.task.dreamfield.domain.word.request

data class AddWordRequest(
        val value: String,
        val description: String,
        val maxScores: Int
)