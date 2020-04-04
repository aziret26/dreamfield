package kg.task.dreamfield.service.word

import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.repository.word.PlayerCurrentWordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerCurrentWordService {
    fun findByPlayerAndWord(player: Player, word: Word): PlayerCurrentWord
    fun create(player: Player, word: Word): PlayerCurrentWord
    fun update(playerCurrentWord: PlayerCurrentWord, triesCountValue: Int, isLastTryValue: Boolean): PlayerCurrentWord
}

@Service
internal class DefaultPlayerCurrentWordService(
        private val playerCurrentWordRepository: PlayerCurrentWordRepository
) : PlayerCurrentWordService {

    @Transactional(readOnly = true)
    override fun findByPlayerAndWord(player: Player, word: Word): PlayerCurrentWord {
        return playerCurrentWordRepository.findByPlayerAndWord(player, word)
    }

    @Transactional
    override fun create(player: Player, word: Word): PlayerCurrentWord {
        val playerCurrentWord = PlayerCurrentWord(
                player = player,
                word = word,
                triesCount = 0,
                isLastTry = false
        )

        return playerCurrentWordRepository.save(playerCurrentWord)
    }

    @Transactional
    override fun update(playerCurrentWord: PlayerCurrentWord,
                        triesCountValue: Int,
                        isLastTryValue: Boolean): PlayerCurrentWord {
        return playerCurrentWord.apply {
            triesCount = triesCountValue
            isLastTry = isLastTryValue

            playerCurrentWordRepository.save(this)
        }
    }

}