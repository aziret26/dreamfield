package kg.task.dreamfield.domain.word.paging

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.PageRequestBase
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.paging.SortProvider
import kg.task.dreamfield.domain.word.QWord.word
import kg.task.dreamfield.domain.word.WordStatus
import org.springframework.data.domain.Sort
import org.springframework.data.querydsl.QSort

data class WordSearchRequest(
        val pageRequest: WordPageRequest,
        val filter: WordFilterRequest
)

data class WordPageRequest(
        override val pageInfo: PageInfo?,
        override val sortInfo: SortInfo<WordSort>
) : PageRequestBase<WordSort>

enum class WordSort : SortProvider {
    ID {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, word.id))
    },
    VALUE {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, word.value))
    }
}


private val Sort.Direction.order: Order
    get() = when (this) {
        Sort.Direction.ASC -> Order.ASC
        Sort.Direction.DESC -> Order.DESC
    }


data class WordFilterRequest(
        val searchParameter: String?,
        val status: WordStatus?
)