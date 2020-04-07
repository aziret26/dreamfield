package kg.task.dreamfield.dto.http.statistics.paging

import kg.task.dreamfield.domain.statistics.paging.WordStatisticsSort
import kg.task.dreamfield.dto.http.paging.PageRequestDto
import kg.task.dreamfield.dto.http.paging.SortRequestDtoBase
import org.springframework.data.domain.Sort
import javax.validation.Valid

data class WordStatisticsSearchRequestDto(
        @field:Valid
        val pageRequest: PageRequestDto = PageRequestDto(),

        @field:Valid
        val sorting: WordStatisticsSortRequestDto = WordStatisticsSortRequestDto()
)

data class WordStatisticsSortRequestDto(
        override val sortBy: WordStatisticsSort = WordStatisticsSort.ID,
        override val sortDirection: Sort.Direction = Sort.Direction.ASC
) : SortRequestDtoBase<WordStatisticsSort>