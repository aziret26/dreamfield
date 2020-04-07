package kg.task.dreamfield.service.statistics

import kg.task.dreamfield.domain.statistics.WordStatistics
import kg.task.dreamfield.domain.statistics.paging.WordStatisticsSearchRequest
import kg.task.dreamfield.repository.statistics.WordStatisticsRepository
import kg.task.dreamfield.repository.findAll
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

interface WordStatisticsService {
    fun search(request: WordStatisticsSearchRequest): Page<WordStatistics>
}

@Service
internal class DefaultWordStatisticsService(
        private val playerStatisticsRepository: WordStatisticsRepository
) : WordStatisticsService {

    override fun search(request: WordStatisticsSearchRequest): Page<WordStatistics> {
        return playerStatisticsRepository.findAll(request.pageRequest)
    }

}