package kg.task.dreamfield.service.statistics

import kg.task.dreamfield.domain.statistics.PlayerWordStatistics
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.user.User
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.exception.NotFoundException
import kg.task.dreamfield.repository.statistics.PlayerWordStatisticsRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerWordStatisticsService {
    fun findById(id: Long): PlayerWordStatistics?
    fun getById(id: Long): PlayerWordStatistics
    fun findByPlayer(user: Player): Collection<PlayerWordStatistics>
    fun findByWord(word: Word): Collection<PlayerWordStatistics>
    fun findByPlayerAndWord(user: Player, word: Word): PlayerWordStatistics?
    fun create(player: Player, word: Word): PlayerWordStatistics
    fun attemptFailed(playerWordStatistics: PlayerWordStatistics): PlayerWordStatistics
    fun attemptSucceeded(playerWordStatistics: PlayerWordStatistics, score: Int): PlayerWordStatistics
}

@Service
internal class DefaultPlayerWordStatisticsService(
        private val playerWordStatisticsRepository: PlayerWordStatisticsRepository
) : PlayerWordStatisticsService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): PlayerWordStatistics? {
        return playerWordStatisticsRepository.findByIdOrNull(id)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): PlayerWordStatistics {
        return findById(id)
                ?: throw NotFoundException(PlayerWordStatistics::class, id, "id")
    }

    @Transactional(readOnly = true)
    override fun findByPlayer(user: Player): Collection<PlayerWordStatistics> {
        return playerWordStatisticsRepository.findByPlayer(user)
    }

    @Transactional(readOnly = true)
    override fun findByWord(word: Word): Collection<PlayerWordStatistics> {
        return playerWordStatisticsRepository.findByWord(word)
    }

    @Transactional(readOnly = true)
    override fun findByPlayerAndWord(user: Player, word: Word): PlayerWordStatistics? {
        return playerWordStatisticsRepository.findByPlayerAndWord(user, word)
    }

    @Transactional
    override fun create(player: Player, word: Word): PlayerWordStatistics {
        val playerWordStatistics = PlayerWordStatistics(
                word = word,
                player = player,
                attemptsFailed = 0,
                attemptsSuccess = 0,
                scoresAchieved = 0
        )
        return playerWordStatisticsRepository.save(playerWordStatistics)
    }

    @Transactional
    override fun attemptFailed(playerWordStatistics: PlayerWordStatistics): PlayerWordStatistics {
        return playerWordStatistics.apply {
            attemptsFailed++

            playerWordStatisticsRepository.save(this)
        }
    }

    @Transactional
    override fun attemptSucceeded(playerWordStatistics: PlayerWordStatistics,
                                  score: Int): PlayerWordStatistics {
        return playerWordStatistics.apply {
            attemptsSuccess++
            scoresAchieved += score

            playerWordStatisticsRepository.save(this)
        }
    }

}