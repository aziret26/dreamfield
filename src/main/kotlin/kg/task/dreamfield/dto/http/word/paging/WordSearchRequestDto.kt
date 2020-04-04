package kg.task.dreamfield.dto.http.word.paging

import kg.task.dreamfield.domain.word.WordStatus
import kg.task.dreamfield.domain.word.paging.WordSort
import kg.task.dreamfield.dto.http.paging.PageRequestDto
import kg.task.dreamfield.dto.http.paging.SortRequestDtoBase
import org.springframework.data.domain.Sort
import javax.validation.Valid
import javax.validation.constraints.Size

data class WordSearchRequestDto(
        @field:Valid
        val pageRequest: PageRequestDto = PageRequestDto(),

        @field:Valid
        val sorting: WordSortRequestDto = WordSortRequestDto(),

        @field:Valid
        val filters: WordFilterRequestDto?
)

data class WordSortRequestDto(
        override val sortBy: WordSort = WordSort.VALUE,
        override val sortDirection: Sort.Direction = Sort.Direction.ASC
) : SortRequestDtoBase<WordSort>

data class WordFilterRequestDto(
        @field:Size(min = 1, max = 255)
        val value: String?,

        val status: WordStatus?
)
