package kg.task.dreamfield.endpoint.word

import kg.task.dreamfield.config.security.principal.PlayerPrincipal
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.service.user.PlayerUserService
import kg.task.dreamfield.service.word.PlayerCurrentWordCreateService
import kg.task.dreamfield.service.word.PlayerCurrentWordSearchService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface WordSearchEndpoint {
    fun getNextWordRandomWord(playerPrincipal: PlayerPrincipal): Word
}

@Service
internal class DefaultWordSearchEndpoint(
        private val playerCurrentWordSearchService: PlayerCurrentWordSearchService,
        private val playerUserWordCreateService: PlayerCurrentWordCreateService,
        private val playerUserService: PlayerUserService
) : WordSearchEndpoint {

    @Transactional
    override fun getNextWordRandomWord(playerPrincipal: PlayerPrincipal): Word {
        val player: Player = playerUserService.getById(playerPrincipal.id)
        val word: Word = playerCurrentWordSearchService.getNextWordVisibleRandom(player)

        playerUserWordCreateService.createOrReplace(player, word)

        return word
    }


}