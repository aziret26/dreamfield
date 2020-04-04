package kg.task.dreamfield.repository.word

import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.repository.BaseRepository

interface PlayerCurrentWordRepository : BaseRepository<PlayerCurrentWord> {
    fun findByPlayerAndWord(user: Player, word: Word): PlayerCurrentWord
}