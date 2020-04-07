package kg.task.dreamfield.mapper.statistics

import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.statistics.PlayerStatistics
import kg.task.dreamfield.domain.statistics.paging.PlayerStatisticsPageRequest
import kg.task.dreamfield.domain.statistics.paging.PlayerStatisticsSearchRequest
import kg.task.dreamfield.dto.http.statistics.PlayerStatisticsDto
import kg.task.dreamfield.dto.http.statistics.paging.PlayerStatisticsSearchRequestDto
import kg.task.dreamfield.mapper.user.PlayerUserMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerStatisticsMapper {
    fun toPlayerStatisticsDto(playerStatistics: PlayerStatistics): PlayerStatisticsDto
    fun toPlayerStatisticsSearchRequest(requestDto: PlayerStatisticsSearchRequestDto): PlayerStatisticsSearchRequest
}

@Service
internal class DefaultPlayerStatisticsMapper(
        private val playerUserMapper: PlayerUserMapper
) : PlayerStatisticsMapper {

    @Transactional(readOnly = true)
    override fun toPlayerStatisticsDto(playerStatistics: PlayerStatistics): PlayerStatisticsDto {
        return PlayerStatisticsDto(
                id = playerStatistics.id!!,
                attemptsSuccess = playerStatistics.attemptsSuccess,
                attemptsFailed = playerStatistics.attemptsFailed,
                attemptsTotal = playerStatistics.attemptsTotal,
                scoresAchieved = playerStatistics.scoresAchieved,
                uniqueWords = playerStatistics.uniqueWords,
                player = playerUserMapper.toPlayerDto(playerStatistics.player)
        )
    }

    override fun toPlayerStatisticsSearchRequest(requestDto: PlayerStatisticsSearchRequestDto): PlayerStatisticsSearchRequest {
        return PlayerStatisticsSearchRequest(
                pageRequest = PlayerStatisticsPageRequest(
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