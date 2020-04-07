package kg.task.dreamfield.mapper.statistics

import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.statistics.WordStatistics
import kg.task.dreamfield.domain.statistics.paging.WordStatisticsPageRequest
import kg.task.dreamfield.domain.statistics.paging.WordStatisticsSearchRequest
import kg.task.dreamfield.dto.http.statistics.WordStatisticsDto
import kg.task.dreamfield.dto.http.statistics.paging.WordStatisticsSearchRequestDto
import kg.task.dreamfield.mapper.word.WordMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface WordStatisticsMapper {
    fun toWordStatisticsDto(wordStatistics: WordStatistics): WordStatisticsDto
    fun toWordStatisticsSearchRequest(requestDto: WordStatisticsSearchRequestDto): WordStatisticsSearchRequest
}

@Service
internal class DefaultWordStatisticsMapper(
        private val wordMapper: WordMapper
) : WordStatisticsMapper {

    @Transactional(readOnly = true)
    override fun toWordStatisticsDto(wordStatistics: WordStatistics): WordStatisticsDto {
        return WordStatisticsDto(
                id = wordStatistics.id!!,
                attemptsFailed = wordStatistics.attemptsFailed,
                attemptsSuccess = wordStatistics.attemptsSuccess,
                attemptsTotal = wordStatistics.attemptsTotal,
                uniquePlayers = wordStatistics.uniquePlayers,
                maxScoreAchieved = wordStatistics.maxScoreAchieved,
                word = wordMapper.toWordDto(wordStatistics.word)
        )
    }

    override fun toWordStatisticsSearchRequest(requestDto: WordStatisticsSearchRequestDto): WordStatisticsSearchRequest {
        return WordStatisticsSearchRequest(
                pageRequest = WordStatisticsPageRequest(
                        pageInfo = PageInfo(
                                page = requestDto.pageRequest.page,
                                limit = requestDto.pageRequest.limit
                        ),
                        sortInfo = SortInfo(
                                sortBy = requestDto.sorting.sortBy,
                                sortDirection = requestDto.sorting.sortDirection
                        )
                )
        )
    }

}