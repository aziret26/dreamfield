package kg.task.dreamfield.endpoint.statistics

import kg.task.dreamfield.domain.statistics.WordStatistics
import kg.task.dreamfield.domain.statistics.paging.WordStatisticsSearchRequest
import kg.task.dreamfield.dto.http.paging.PageResponseDto
import kg.task.dreamfield.dto.http.statistics.WordStatisticsDto
import kg.task.dreamfield.dto.http.statistics.paging.WordStatisticsSearchRequestDto
import kg.task.dreamfield.mapper.paging.PageMapper
import kg.task.dreamfield.mapper.statistics.WordStatisticsMapper
import kg.task.dreamfield.service.statistics.WordStatisticsService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

interface WordStatisticsEndpoint {
    fun search(requestDto: WordStatisticsSearchRequestDto): PageResponseDto<WordStatisticsDto>
}

@Service
internal class DefaultWordStatisticsEndpoint(
        private val wordStatisticsService: WordStatisticsService,
        private val wordStatisticsMapper: WordStatisticsMapper,
        private val pageMapper: PageMapper
) : WordStatisticsEndpoint {

    override fun search(requestDto: WordStatisticsSearchRequestDto): PageResponseDto<WordStatisticsDto> {
        val request: WordStatisticsSearchRequest = wordStatisticsMapper.toWordStatisticsSearchRequest(requestDto)
        val page: Page<WordStatistics> = wordStatisticsService.search(request)
        val playerStatisticsDtos: Collection<WordStatisticsDto> = page.content.map { wordStatisticsMapper.toWordStatisticsDto(it) }

        return pageMapper.toPageResponseDto(page, playerStatisticsDtos)
    }

}