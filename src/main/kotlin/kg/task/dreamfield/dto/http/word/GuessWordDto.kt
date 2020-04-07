package kg.task.dreamfield.dto.http.word

import kg.task.dreamfield.endpoint.dto.guess.GuessResult

data class GuessWordDto(
        val description: String,
        val length: Int,
        val maxScores: Int,
        val availableScore: Int,
        val guessResult: GuessResult?
)