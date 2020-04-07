package kg.task.dreamfield.mapper.word

import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.dto.http.word.GuessWordDto
import kg.task.dreamfield.endpoint.dto.guess.GuessResult
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerCurrentWordMapper {
    fun toGuessWordDto(playerCurrentWord: PlayerCurrentWord, guessResult: GuessResult? = null): GuessWordDto
}

@Service
internal class DefaultPlayerCurrentWordMapper : PlayerCurrentWordMapper {

    @Transactional(readOnly = true)
    override fun toGuessWordDto(playerCurrentWord: PlayerCurrentWord, guessResult: GuessResult?): GuessWordDto {
        return GuessWordDto(
                description = playerCurrentWord.word.description,
                length = playerCurrentWord.word.value.length,
                maxScores = playerCurrentWord.word.maxScores,
                availableScore = playerCurrentWord.scoreAvailable,
                guessResult = guessResult
        )
    }

}