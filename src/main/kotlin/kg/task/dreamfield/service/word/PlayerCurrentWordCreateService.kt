package kg.task.dreamfield.service.word

import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.domain.word.Word
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerCurrentWordCreateService {
    fun createOrReplace(player: Player, word: Word): PlayerCurrentWord
}

@Service
internal class DefaultPlayerCurrentWordCreateService(
        private val playerCurrentWordService: PlayerCurrentWordService
) : PlayerCurrentWordCreateService {

    @Transactional
    override fun createOrReplace(player: Player, word: Word): PlayerCurrentWord {
        playerCurrentWordService.removeByPlayer(player)

        return playerCurrentWordService.create(player, word)
    }

}