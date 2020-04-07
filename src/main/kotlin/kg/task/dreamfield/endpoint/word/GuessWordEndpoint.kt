package kg.task.dreamfield.endpoint.word

import kg.task.dreamfield.config.security.principal.PlayerPrincipal
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.dto.http.word.GuessWordDto
import kg.task.dreamfield.dto.http.word.GuessWordRequestDto
import kg.task.dreamfield.endpoint.dto.guess.GuessResult
import kg.task.dreamfield.mapper.word.PlayerCurrentWordMapper
import kg.task.dreamfield.service.user.PlayerUserService
import kg.task.dreamfield.service.word.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface GuessWordEndpoint {
    fun getNextGuessWord(playerPrincipal: PlayerPrincipal): GuessWordDto
    fun guessWord(playerPrincipal: PlayerPrincipal, requestDto: GuessWordRequestDto): GuessWordDto
}

@Service
internal class DefaultGuessWordEndpoint(
        private val playerCurrentWordSearchService: PlayerCurrentWordSearchService,
        private val playerCurrentWordCreateService: PlayerCurrentWordCreateService,
        private val playerCurrentWordGuessService: PlayerCurrentWordGuessService,
        private val playerCurrentWordService: PlayerCurrentWordService,
        private val playerUserService: PlayerUserService,
        private val playerCurrentWordMapper: PlayerCurrentWordMapper
) : GuessWordEndpoint {

    @Transactional
    override fun getNextGuessWord(playerPrincipal: PlayerPrincipal): GuessWordDto {
        val player: Player = playerUserService.getById(playerPrincipal.id)
        val nextWord: Word = playerCurrentWordSearchService.getNextWordVisibleRandom(player)

        val playerCurrentWord: PlayerCurrentWord = playerCurrentWordCreateService.createOrReplace(player, nextWord)

        return playerCurrentWordMapper.toGuessWordDto(playerCurrentWord)
    }

    @Transactional
    override fun guessWord(playerPrincipal: PlayerPrincipal, requestDto: GuessWordRequestDto): GuessWordDto {
        val player: Player = playerUserService.getById(playerPrincipal.id)
        val playerCurrentWord: PlayerCurrentWord = playerCurrentWordService.getByPlayer(player)
        val guessResultResult: GuessResult = playerCurrentWordGuessService.guessWord(playerCurrentWord, requestDto)

        val guessResult: GuessResult = playerCurrentWordGuessService.processGuessResult(playerCurrentWord, guessResultResult)

        return playerCurrentWordMapper.toGuessWordDto(playerCurrentWord, guessResult)

    }

}