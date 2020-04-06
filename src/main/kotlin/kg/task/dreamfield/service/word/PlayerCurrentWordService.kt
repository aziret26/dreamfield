package kg.task.dreamfield.service.word

import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.exception.NotFoundException
import kg.task.dreamfield.repository.word.PlayerCurrentWordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerCurrentWordService {
    fun existByPlayer(player: Player): Boolean
    fun findByPlayer(player: Player): PlayerCurrentWord?
    fun getByPlayer(player: Player): PlayerCurrentWord
    fun create(player: Player, word: Word): PlayerCurrentWord
    fun updateAvailableScore(playerCurrentWord: PlayerCurrentWord, scoreAvailableNew: Int): PlayerCurrentWord
    fun removeByPlayer(player: Player)
}

@Service
internal class DefaultPlayerCurrentWordService(
        private val playerCurrentWordRepository: PlayerCurrentWordRepository
) : PlayerCurrentWordService {

    @Transactional(readOnly = true)
    override fun existByPlayer(player: Player): Boolean {
        return playerCurrentWordRepository.existsByPlayer(player)
    }

    @Transactional(readOnly = true)
    override fun findByPlayer(player: Player): PlayerCurrentWord? {
        return playerCurrentWordRepository.findByPlayer(player)
    }

    @Transactional(readOnly = true)
    override fun getByPlayer(player: Player): PlayerCurrentWord {
        return findByPlayer(player)
                ?: throw NotFoundException("Current Player has no words to guess")
    }

    @Transactional
    override fun create(player: Player, word: Word): PlayerCurrentWord {
        val playerCurrentWord = PlayerCurrentWord(
                player = player,
                word = word,
                scoreAvailable = word.maxScores
        )

        return playerCurrentWordRepository.save(playerCurrentWord)
    }

    @Transactional
    override fun updateAvailableScore(playerCurrentWord: PlayerCurrentWord,
                                      scoreAvailableNew: Int): PlayerCurrentWord {
        return playerCurrentWord.apply {
            scoreAvailable = scoreAvailableNew

            playerCurrentWordRepository.save(this)
        }
    }

    @Transactional
    override fun removeByPlayer(player: Player) {
        playerCurrentWordRepository.deleteByPlayer(player)
    }

}