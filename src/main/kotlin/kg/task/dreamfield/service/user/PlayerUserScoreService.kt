package kg.task.dreamfield.service.user

import kg.task.dreamfield.domain.statistics.PlayerWordStatistics
import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.service.statistics.PlayerWordStatisticsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerUserScoreService {
    fun attemptSucceeded(playerCurrentWord: PlayerCurrentWord, scoreToAdd: Int)
    fun attemptFailed(playerCurrentWord: PlayerCurrentWord)
}

@Service
internal class DefaultPlayerUserScoreService(
        private val playerWordStatisticsService: PlayerWordStatisticsService,
        private val playerUserService: PlayerUserService
) : PlayerUserScoreService {

    @Transactional
    override fun attemptSucceeded(playerCurrentWord: PlayerCurrentWord, scoreToAdd: Int) {

        playerUserService.addScore(playerCurrentWord.player, scoreToAdd)

        val playerWordStatistics: PlayerWordStatistics = playerWordStatisticsService.findByPlayerAndWord(playerCurrentWord.player, playerCurrentWord.word)
                ?: playerWordStatisticsService.create(playerCurrentWord.player, playerCurrentWord.word)

        playerWordStatisticsService.attemptSucceeded(playerWordStatistics, scoreToAdd)

    }

    override fun attemptFailed(playerCurrentWord: PlayerCurrentWord) {

        val playerWordStatistics: PlayerWordStatistics = playerWordStatisticsService.findByPlayerAndWord(playerCurrentWord.player, playerCurrentWord.word)
                ?: playerWordStatisticsService.create(playerCurrentWord.player, playerCurrentWord.word)

        playerWordStatisticsService.attemptFailed(playerWordStatistics)

    }

}