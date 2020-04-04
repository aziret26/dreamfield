package kg.task.dreamfield.dto.http.paging

import kg.task.dreamfield.domain.paging.SortProvider
import org.springframework.data.domain.Sort

interface SortRequestDtoBase<T : SortProvider> {
    val sortBy: SortProvider
    val sortDirection: Sort.Direction
}
