package kg.task.dreamfield.dto.http.user.paging

import kg.task.dreamfield.domain.user.paging.PlayerSort
import kg.task.dreamfield.dto.http.paging.PageRequestDto
import kg.task.dreamfield.dto.http.paging.SortRequestDtoBase
import org.springframework.data.domain.Sort
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.Size

class PlayerSearchRequestDto(
        @field:Valid
        val pageRequest: PageRequestDto = PageRequestDto(),

        @field:Valid
        val sorting: PlayerSortRequestDto = PlayerSortRequestDto(),

        @field:Valid
        val filters: PlayerFilterRequestDto?
)

data class PlayerSortRequestDto(
        override val sortBy: PlayerSort = PlayerSort.NAME,
        override val sortDirection: Sort.Direction = Sort.Direction.ASC
) : SortRequestDtoBase<PlayerSort>

data class PlayerFilterRequestDto(
        @field:Size(min = 1, max = 255)
        val name: String?,

        @field:Email
        @field:Size(min = 1, max = 255)
        val email: String?

)