package kg.task.dreamfield.domain.user.paging

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.PageRequestBase
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.paging.SortProvider
import kg.task.dreamfield.domain.user.QPlayer.player
import org.springframework.data.domain.Sort
import org.springframework.data.querydsl.QSort

data class PlayerSearchRequest(
        val pageRequest: PlayerPageRequest,
        val filter: PlayerFilterRequest
)

data class PlayerPageRequest(
        override val pageInfo: PageInfo?,
        override val sortInfo: SortInfo<PlayerSort>
) : PageRequestBase<PlayerSort>

enum class PlayerSort : SortProvider {
    ID {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, player.id))
    },
    NAME {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, player.name))
    },
    EMAIL {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, player.email))
    },
    SCORE {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, player.score))
    }
}


private val Sort.Direction.order: Order
    get() = when (this) {
        Sort.Direction.ASC -> Order.ASC
        Sort.Direction.DESC -> Order.DESC
    }


data class PlayerFilterRequest(
        val name: String?,
        val email: String?
)