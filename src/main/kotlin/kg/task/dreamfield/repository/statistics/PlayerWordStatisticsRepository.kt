package kg.task.dreamfield.repository.statistics

import kg.task.dreamfield.domain.statistics.PlayerWordStatistics
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.repository.BaseRepository

interface PlayerWordStatisticsRepository : BaseRepository<PlayerWordStatistics> {
    fun findByPlayer(user: Player): Collection<PlayerWordStatistics>
    fun findByWord(word: Word): Collection<PlayerWordStatistics>
    fun findByPlayerAndWord(user: Player, word: Word): PlayerWordStatistics?
}