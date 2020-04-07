package kg.task.dreamfield.repository.statistics

import kg.task.dreamfield.domain.statistics.PlayerStatistics
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.repository.BaseRepository

interface PlayerStatisticsRepository : BaseRepository<PlayerStatistics> {
    fun findByPlayer(player: Player): PlayerStatistics?
}