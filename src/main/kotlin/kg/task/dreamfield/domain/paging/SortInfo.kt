package kg.task.dreamfield.domain.paging

import com.querydsl.core.types.Order
import org.springframework.data.domain.Sort

data class SortInfo<T : SortProvider>(
        val sortBy: T,
        val sortDirection: Sort.Direction
)

interface SortProvider {
    fun getSorting(direction: Sort.Direction): Sort
}

val Sort.Direction.order: Order
    get() = when (this) {
        Sort.Direction.ASC -> Order.ASC
        Sort.Direction.DESC -> Order.DESC
    }