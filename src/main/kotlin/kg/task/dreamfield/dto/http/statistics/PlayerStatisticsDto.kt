package kg.task.dreamfield.dto.http.statistics

import kg.task.dreamfield.dto.http.user.PlayerDto

data class PlayerStatisticsDto(
        val id: Long,
        val uniqueWords: Long,
        val attemptsSuccess: Long,
        val attemptsFailed: Long,
        val attemptsTotal: Long,
        val scoresAchieved: Long,
        val player: PlayerDto
)