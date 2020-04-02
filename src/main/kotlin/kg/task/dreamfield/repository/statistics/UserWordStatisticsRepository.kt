package kg.task.dreamfield.repository.statistics

import kg.task.dreamfield.domain.user.User
import kg.task.dreamfield.domain.word.UserWordStatistics
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.repository.BaseRepository

interface UserWordStatisticsRepository : BaseRepository<UserWordStatistics> {
    fun findByUser(user: User): Collection<UserWordStatistics>
    fun findByWord(word: Word): Collection<UserWordStatistics>
    fun findByUserAndWord(user: User, word: Word): Collection<UserWordStatistics>
}