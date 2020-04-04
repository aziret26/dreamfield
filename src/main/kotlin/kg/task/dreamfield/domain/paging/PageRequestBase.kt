package kg.task.dreamfield.domain.paging

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface PageRequestBase<T : SortProvider> {
    val pageInfo: PageInfo?
    val sortInfo: SortInfo<T>
}

fun <T : SortProvider> PageRequestBase<T>.toPageable(): Pageable {
    val sort: Sort = sortInfo.sortBy.getSorting(sortInfo.sortDirection)
    return PageRequest.of(pageInfo!!.page, pageInfo!!.limit, sort)
}