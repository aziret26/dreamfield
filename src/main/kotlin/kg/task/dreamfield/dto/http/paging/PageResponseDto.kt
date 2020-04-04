package kg.task.dreamfield.dto.http.paging

data class PageResponseDto<T>(
        val page: Int,
        val numberOfElements: Int,
        val totalPages: Int,
        val totalElements: Int,
        val content: List<T>
)