package kg.task.dreamfield.dto.http.word

import kg.task.dreamfield.domain.word.WordStatus

data class WordDto(
        val id: Long,
        val value: String,
        val description: String,
        val maxScores: Int,
        val status: WordStatus
)