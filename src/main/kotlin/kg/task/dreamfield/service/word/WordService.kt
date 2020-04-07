package kg.task.dreamfield.service.word

import com.querydsl.core.BooleanBuilder
import kg.task.dreamfield.domain.word.QWord.word
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.domain.word.WordStatus
import kg.task.dreamfield.domain.word.paging.WordFilterRequest
import kg.task.dreamfield.domain.word.paging.WordSearchRequest
import kg.task.dreamfield.domain.word.request.AddWordRequest
import kg.task.dreamfield.exception.NotFoundException
import kg.task.dreamfield.repository.findAll
import kg.task.dreamfield.repository.word.WordRepository
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface WordService {
    fun findById(id: Long): Word?
    fun countAll(): Long
    fun countByStatus(status: WordStatus): Long
    fun getById(id: Long): Word
    fun create(request: AddWordRequest): Word
    fun search(request: WordSearchRequest): Page<Word>
    fun updateStatus(word: Word, newStatus: WordStatus): Word
}

@Service
internal class DefaultWordService(
        private val wordRepository: WordRepository
) : WordService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): Word? {
        return wordRepository.findByIdOrNull(id)
    }

    override fun countAll(): Long {
        return wordRepository.count()
    }

    override fun countByStatus(status: WordStatus): Long {
        return wordRepository.countAllByStatus(status)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): Word {
        return findById(id)
                ?: throw NotFoundException(Word::class, id, "id")
    }

    @Transactional
    override fun create(request: AddWordRequest): Word {
        val word = Word(
                value = request.value,
                description = request.description,
                status = WordStatus.VISIBLE,
                maxScores = request.maxScores
        )

        return wordRepository.save(word)
    }

    override fun updateStatus(word: Word, newStatus: WordStatus): Word {
        return word.apply {
            status = newStatus

            wordRepository.save(this)
        }

    }

    @Transactional(readOnly = true)
    override fun search(request: WordSearchRequest): Page<Word> {
        val predicate = BooleanBuilder()
        search(predicate, request.filter)

        return wordRepository.findAll(predicate, request.pageRequest)
    }

    private fun search(predicate: BooleanBuilder, filter: WordFilterRequest) {
        filter.status?.let { predicate.and(word.status.eq(it)) }
        filter.value?.let { predicate.and(word.value.containsIgnoreCase(it)) }
    }
}