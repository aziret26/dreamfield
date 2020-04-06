package kg.task.dreamfield.mapper.word

import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.dto.http.word.GuessWordDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerCurrentWordMapper {
    fun toGuessWordDto(playerCurrentWord: PlayerCurrentWord): GuessWordDto
}

@Service
internal class DefaultPlayerCurrentWordMapper : PlayerCurrentWordMapper {

    @Transactional(readOnly = true)
    override fun toGuessWordDto(playerCurrentWord: PlayerCurrentWord): GuessWordDto {
        return GuessWordDto(
                description = playerCurrentWord.word.description,
                availableScore = playerCurrentWord.scoreAvailable,
                maxScores = playerCurrentWord.word.maxScores
        )
    }

}