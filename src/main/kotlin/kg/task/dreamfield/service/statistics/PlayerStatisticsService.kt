package kg.task.dreamfield.service.statistics

import kg.task.dreamfield.domain.statistics.PlayerStatistics
import kg.task.dreamfield.domain.statistics.paging.PlayerStatisticsSearchRequest
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.repository.statistics.PlayerStatisticsRepository
import kg.task.dreamfield.repository.findAll
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

interface PlayerStatisticsService {
    fun findByPlayer(player: Player): PlayerStatistics?
    fun search(request: PlayerStatisticsSearchRequest): Page<PlayerStatistics>
}

@Service
internal class DefaultPlayerStatisticsService(
        private val playerStatisticsRepository: PlayerStatisticsRepository
) : PlayerStatisticsService {

    override fun findByPlayer(player: Player): PlayerStatistics? {
        return playerStatisticsRepository.findByPlayer(player)
    }

    override fun search(request: PlayerStatisticsSearchRequest): Page<PlayerStatistics> {
        return playerStatisticsRepository.findAll(request.pageRequest)
    }

}