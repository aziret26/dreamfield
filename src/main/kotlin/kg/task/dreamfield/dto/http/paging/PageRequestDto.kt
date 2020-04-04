package kg.task.dreamfield.dto.http.paging

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class PageRequestDto(
        @field:Min(0)
        val page: Int = 1,

        @field:Min(0)
        @field:Max(50)
        val limit: Int = MAX_LIMIT
) {
    companion object {
        private const val MAX_LIMIT = 50
    }
}