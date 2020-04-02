package kg.task.dreamfield.repository.word

import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.repository.BaseRepository

interface WordRepository : BaseRepository<Word> {

    fun findByValueContaining(value: String): Word

    fun findByValue(value: String): Word

}