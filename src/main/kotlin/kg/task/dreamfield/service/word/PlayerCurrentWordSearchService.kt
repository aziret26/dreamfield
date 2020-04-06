package kg.task.dreamfield.service.word

import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.domain.word.WordStatus
import kg.task.dreamfield.domain.word.paging.WordFilterRequest
import kg.task.dreamfield.domain.word.paging.WordPageRequest
import kg.task.dreamfield.domain.word.paging.WordSearchRequest
import kg.task.dreamfield.domain.word.paging.WordSort
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

interface PlayerCurrentWordSearchService {
    fun getNextWordVisibleRandom(player: Player): Word
}

@Service
internal class DefaultPlayerCurrentWordSearchService(
        private val wordService: DefaultWordService
) : PlayerCurrentWordSearchService {

    @Transactional
    override fun getNextWordVisibleRandom(player: Player): Word {
        val visibleWordsCount = wordService.countByStatus(WordStatus.VISIBLE)
        val randomPage: Int = Random.nextLong(1, visibleWordsCount).toInt()

        val request: WordSearchRequest = getRandomWordSearchRequest(randomPage)
        val page: Page<Word> = wordService.search(request)

        return if (page.content.isNotEmpty()) {
            page.content.first()
        } else {
            getNextWordVisibleRandom(player)
        }
    }

    private fun getRandomWordSearchRequest(page: Int): WordSearchRequest {
        return WordSearchRequest(
                pageRequest = WordPageRequest(
                        pageInfo = PageInfo(
                                page = page,
                                limit = 1
                        ),
                        sortInfo = SortInfo(
                                sortBy = WordSort.ID,
                                sortDirection = Sort.Direction.ASC
                        )
                ),
                filter = WordFilterRequest(
                        status = WordStatus.VISIBLE,
                        searchParameter = null
                )
        )
    }

}