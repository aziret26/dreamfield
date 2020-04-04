package kg.task.dreamfield.mapper.paging

import kg.task.dreamfield.domain.BaseEntity
import kg.task.dreamfield.dto.http.paging.PageResponseDto
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

interface PageMapper {
    fun <M : BaseEntity, D> toPageResponseDto(page: Page<M>, content: Collection<D>): PageResponseDto<D>
}

@Service
internal class DefaultPageMapper : PageMapper {
    override fun <M : BaseEntity, D> toPageResponseDto(page: Page<M>, content: Collection<D>): PageResponseDto<D> {
        return PageResponseDto(
                page = page.pageable.pageNumber + 1,
                totalPages = page.totalPages,
                numberOfElements = page.numberOfElements,
                totalElements = page.totalElements.toInt(),
                content = content.toList()
        )
    }

}
