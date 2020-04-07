package kg.task.dreamfield.endpoint.statistics

import kg.task.dreamfield.config.security.principal.PlayerPrincipal
import kg.task.dreamfield.domain.statistics.PlayerStatistics
import kg.task.dreamfield.domain.statistics.paging.PlayerStatisticsSearchRequest
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.dto.http.paging.PageResponseDto
import kg.task.dreamfield.dto.http.statistics.PlayerStatisticsDto
import kg.task.dreamfield.dto.http.statistics.paging.PlayerStatisticsSearchRequestDto
import kg.task.dreamfield.mapper.paging.PageMapper
import kg.task.dreamfield.mapper.statistics.PlayerStatisticsMapper
import kg.task.dreamfield.service.statistics.PlayerStatisticsService
import kg.task.dreamfield.service.user.PlayerUserService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

interface PlayerStatisticsEndpoint {
    fun getPlayerStatistics(playerPrincipal: PlayerPrincipal): PlayerStatisticsDto?
    fun search(requestDto: PlayerStatisticsSearchRequestDto): PageResponseDto<PlayerStatisticsDto>
}

@Service
internal class DefaultPlayerStatisticsEndpoint(
        private val playerStatisticsService: PlayerStatisticsService,
        private val playerUserService: PlayerUserService,
        private val playerStatisticsMapper: PlayerStatisticsMapper,
        private val pageMapper: PageMapper
) : PlayerStatisticsEndpoint {
    override fun getPlayerStatistics(playerPrincipal: PlayerPrincipal): PlayerStatisticsDto? {
        val player: Player = playerUserService.getById(playerPrincipal.id)

        val playerStatistics: PlayerStatistics? = playerStatisticsService.findByPlayer(player)

        return playerStatistics?.let { playerStatisticsMapper.toPlayerStatisticsDto(it) }
    }

    override fun search(requestDto: PlayerStatisticsSearchRequestDto): PageResponseDto<PlayerStatisticsDto> {
        val request: PlayerStatisticsSearchRequest = playerStatisticsMapper.toPlayerStatisticsSearchRequest(requestDto)
        val page: Page<PlayerStatistics> = playerStatisticsService.search(request)
        val playerStatisticsDtos: Collection<PlayerStatisticsDto> = page.content.map { playerStatisticsMapper.toPlayerStatisticsDto(it) }

        return pageMapper.toPageResponseDto(page, playerStatisticsDtos)
    }

}