package kg.task.dreamfield.dto.http.user.paging

import kg.task.dreamfield.domain.user.paging.AdminSort
import kg.task.dreamfield.dto.http.paging.PageRequestDto
import kg.task.dreamfield.dto.http.paging.SortRequestDtoBase
import org.springframework.data.domain.Sort
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.Size

class AdminSearchRequestDto(
        @field:Valid
        val pageRequest: PageRequestDto = PageRequestDto(),

        @field:Valid
        val sorting: AdminSortRequestDto = AdminSortRequestDto(),

        @field:Valid
        val filters: AdminFilterRequestDto?
)

data class AdminSortRequestDto(
        override val sortBy: AdminSort = AdminSort.NAME,
        override val sortDirection: Sort.Direction = Sort.Direction.ASC
) : SortRequestDtoBase<AdminSort>

data class AdminFilterRequestDto(
        @field:Size(min = 1, max = 255)
        val name: String?,

        @field:Email
        @field:Size(min = 1, max = 255)
        val email: String?

)