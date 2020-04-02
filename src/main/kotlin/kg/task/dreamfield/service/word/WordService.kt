package kg.task.dreamfield.service.word

import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.exception.NotFoundException
import kg.task.dreamfield.repository.word.WordRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface WordService {
    fun findById(id: Long): Word?
    fun getById(id: Long): Word
}

@Service
internal class DefaultWordService(
        private val wordRepository: WordRepository
) : WordService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): Word? {
        return wordRepository.findByIdOrNull(id)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): Word {
        return findById(id)
                ?: throw NotFoundException(Word::class, id, "id")
    }

}