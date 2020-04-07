package kg.task.dreamfield.domain.statistics.paging

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.PageRequestBase
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.paging.SortProvider
import kg.task.dreamfield.domain.statistics.QPlayerStatistics.playerStatistics
import org.springframework.data.domain.Sort
import org.springframework.data.querydsl.QSort

data class PlayerStatisticsSearchRequest(
        val pageRequest: PlayerStatisticsPageRequest
)

data class PlayerStatisticsPageRequest(
        override val pageInfo: PageInfo?,
        override val sortInfo: SortInfo<PlayerStatisticsSort>
) : PageRequestBase<PlayerStatisticsSort>

enum class PlayerStatisticsSort : SortProvider {
    ID {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, playerStatistics.id))
    },
    ATTEMPTS_SUCCESS {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, playerStatistics.attemptsSuccess))
    },
    ATTEMPTS_FAILED {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, playerStatistics.attemptsFailed))
    },
    ATTEMPTS_TOTAL {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, playerStatistics.attemptsTotal))
    },
    TOTAL_SCORES {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, playerStatistics.scoresAchieved))
    },
    UNIQUE_WORDS {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, playerStatistics.uniqueWords))
    }
}

private val Sort.Direction.order: Order
    get() = when (this) {
        Sort.Direction.ASC -> Order.ASC
        Sort.Direction.DESC -> Order.DESC
    }
