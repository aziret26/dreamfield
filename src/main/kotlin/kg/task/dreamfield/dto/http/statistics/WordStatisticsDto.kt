package kg.task.dreamfield.dto.http.statistics

import kg.task.dreamfield.dto.http.word.WordDto

data class WordStatisticsDto(
        val id: Long,
        val uniquePlayers: Long,
        val attemptsSuccess: Long,
        val attemptsFailed: Long,
        val attemptsTotal: Long,
        val maxScoreAchieved: Int,
        val word: WordDto
)