package kg.task.dreamfield.dto.http.statistics.paging

import kg.task.dreamfield.domain.statistics.paging.PlayerStatisticsSort
import kg.task.dreamfield.dto.http.paging.PageRequestDto
import kg.task.dreamfield.dto.http.paging.SortRequestDtoBase
import org.springframework.data.domain.Sort
import javax.validation.Valid

data class PlayerStatisticsSearchRequestDto(
        @field:Valid
        val pageRequest: PageRequestDto = PageRequestDto(),

        @field:Valid
        val sorting: PlayerStatisticsSortRequestDto = PlayerStatisticsSortRequestDto()
)

data class PlayerStatisticsSortRequestDto(
        override val sortBy: PlayerStatisticsSort = PlayerStatisticsSort.ID,
        override val sortDirection: Sort.Direction = Sort.Direction.ASC
) : SortRequestDtoBase<PlayerStatisticsSort>