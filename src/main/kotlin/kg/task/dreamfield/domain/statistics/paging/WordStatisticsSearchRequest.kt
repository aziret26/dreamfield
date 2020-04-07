package kg.task.dreamfield.domain.statistics.paging

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.PageRequestBase
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.paging.SortProvider
import kg.task.dreamfield.domain.statistics.QWordStatistics.wordStatistics
import org.springframework.data.domain.Sort
import org.springframework.data.querydsl.QSort

data class WordStatisticsSearchRequest(
        val pageRequest: WordStatisticsPageRequest
)

data class WordStatisticsPageRequest(
        override val pageInfo: PageInfo?,
        override val sortInfo: SortInfo<WordStatisticsSort>
) : PageRequestBase<WordStatisticsSort>

enum class WordStatisticsSort : SortProvider {
    ID {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, wordStatistics.id))
    },
    ATTEMPTS_SUCCESS {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, wordStatistics.attemptsSuccess))
    },
    ATTEMPTS_FAILED {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, wordStatistics.attemptsFailed))
    },
    ATTEMPTS_TOTAL {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, wordStatistics.attemptsTotal))
    },
    MAX_SCORE_ACHIEVED {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, wordStatistics.maxScoreAchieved))
    },
    UNIQUE_PLAYERS {
        override fun getSorting(direction: Sort.Direction): Sort = QSort(OrderSpecifier(direction.order, wordStatistics.uniquePlayers))
    }
}

private val Sort.Direction.order: Order
    get() = when (this) {
        Sort.Direction.ASC -> Order.ASC
        Sort.Direction.DESC -> Order.DESC
    }
