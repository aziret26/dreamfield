package kg.task.dreamfield.service.word

import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.dto.http.word.GuessWordRequestDto
import kg.task.dreamfield.endpoint.dto.guess.*
import kg.task.dreamfield.service.user.PlayerUserScoreService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerCurrentWordGuessService {
    fun guessWord(playerCurrentWord: PlayerCurrentWord, requestDto: GuessWordRequestDto): GuessResult
    fun analyzeGuessResult(playerCurrentWord: PlayerCurrentWord, guessResult: GuessResult): GuessResult
    fun processGuessResult(playerCurrentWord: PlayerCurrentWord, guessResult: GuessResult): GuessResult
}

@Service
internal class DefaultPlayerCurrentWordGuessService(
        private val playerUserScoreService: PlayerUserScoreService,
        private val playerCurrentWordService: PlayerCurrentWordService
) : PlayerCurrentWordGuessService {

    @Transactional
    override fun guessWord(playerCurrentWord: PlayerCurrentWord,
                           requestDto: GuessWordRequestDto): GuessResult {
        val currentWord: String = playerCurrentWord.word.value

        val guessResult: GuessResultInProgress = if (currentWord == requestDto.word) {
            GuessResultInProgress(
                    lettersContained = getContainingLetters(playerCurrentWord.word.value)
            )
        } else {
            val guess: MutableSet<Char> = mutableSetOf()
            guess.addAll(requestDto.word.toSet())
            guess.addAll(requestDto.fundLetter.map { it.letter }.toSet())

            val foundLetters = mutableListOf<PositionLetterPair>()
            val lettersNotContained = mutableListOf<Char>()

            // get found letters position
            currentWord.forEachIndexed { i, letter ->
                if (guess.contains(letter)) {
                    foundLetters.add(PositionLetterPair(position = i, letter = letter))
                }
            }

            // get wrong guessed letters
            guess.forEach { letter ->
                if (!currentWord.contains(letter)) {
                    lettersNotContained.add(letter)
                }
            }


            GuessResultInProgress(
                    lettersContained = foundLetters,
                    lettersNotContained = lettersNotContained
            )

        }

        return analyzeGuessResult(playerCurrentWord, guessResult)
    }

    override fun analyzeGuessResult(playerCurrentWord: PlayerCurrentWord,
                                    guessResult: GuessResult): GuessResult {
        return when (guessResult) {
            is GuessResultFailed -> guessResult
            is GuessResultFinished -> guessResult

            is GuessResultInProgress -> {
                val score = playerCurrentWord.scoreAvailable - guessResult.lettersNotContained.size
                if (score < 1) {
                    return GuessResultFailed()
                }

                if (playerCurrentWord.word.value.length == guessResult.lettersContained.size) {
                    GuessResultFinished(
                            lettersContained = guessResult.lettersContained,
                            lettersNotContained = guessResult.lettersNotContained
                    )
                } else {
                    guessResult
                }

            }


        }
    }

    @Transactional
    override fun processGuessResult(playerCurrentWord: PlayerCurrentWord,
                                    guessResult: GuessResult): GuessResult {
        return when (guessResult) {
            is GuessResultFailed -> {
                playerUserScoreService.attemptFailed(playerCurrentWord)
                playerCurrentWordService.removeByPlayer(playerCurrentWord.player)
                guessResult
            }
            is GuessResultInProgress -> {
                val score = playerCurrentWord.scoreAvailable - guessResult.lettersNotContained.size

                playerCurrentWordService.updateAvailableScore(playerCurrentWord, score)

                return guessResult
            }
            is GuessResultFinished -> {
                val score = playerCurrentWord.scoreAvailable - guessResult.lettersNotContained.size

                playerCurrentWordService.removeByPlayer(playerCurrentWord.player)
                playerUserScoreService.attemptSucceeded(playerCurrentWord, score)
                guessResult
            }

        }
    }

}

private fun getContainingLetters(word: String): MutableList<PositionLetterPair> {
    val result = mutableListOf<PositionLetterPair>()

    word.forEachIndexed { i, letter ->
        result.add(PositionLetterPair(position = i, letter = letter))
    }

    return result

}

