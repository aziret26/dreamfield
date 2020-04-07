package kg.task.dreamfield.controller

import kg.task.dreamfield.dto.http.paging.PageResponseDto
import kg.task.dreamfield.dto.http.statistics.PlayerStatisticsDto
import kg.task.dreamfield.dto.http.statistics.WordStatisticsDto
import kg.task.dreamfield.dto.http.statistics.paging.PlayerStatisticsSearchRequestDto
import kg.task.dreamfield.dto.http.statistics.paging.WordStatisticsSearchRequestDto
import kg.task.dreamfield.endpoint.statistics.PlayerStatisticsEndpoint
import kg.task.dreamfield.endpoint.statistics.WordStatisticsEndpoint
import kg.task.dreamfield.service.user.UserContextProvider
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/statistics")
class StatisticsController(
        private val wordStatisticsEndpoint: WordStatisticsEndpoint,
        private val playerStatisticsEndpoint: PlayerStatisticsEndpoint,
        private val userContextProvider: UserContextProvider
) {

    @PostMapping("/words/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun searchWordsStatistics(@Valid @RequestBody requestDto: WordStatisticsSearchRequestDto): PageResponseDto<WordStatisticsDto> {
        return wordStatisticsEndpoint.search(requestDto)
    }

    @PostMapping("/players/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun searchPlayersStatistics(@Valid @RequestBody requestDto: PlayerStatisticsSearchRequestDto): PageResponseDto<PlayerStatisticsDto> {
        return playerStatisticsEndpoint.search(requestDto)
    }


    @GetMapping("/players/current")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    fun getCurrentPlayersStatistics(): PlayerStatisticsDto? {
        return playerStatisticsEndpoint.getPlayerStatistics(userContextProvider.getCurrentPlayer())
    }
}