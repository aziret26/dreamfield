package kg.task.dreamfield.domain.statistics

import kg.task.dreamfield.domain.BaseEntity
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.word.Word
import javax.persistence.*

@Entity
@Table(name = "player_word_statistics")
class PlayerWordStatistics(

        @ManyToOne
        @JoinColumn(name = "player_id")
        val player: Player,

        @ManyToOne
        @JoinColumn(name = "word_id")
        val word: Word,

        @Column(name = "best_tries_count")
        var bestTriesCount: Int,

        @Column(name = "attempts_failed")
        var attemptsFailed: Int,

        @Column(name = "attempts_success")
        var attemptsSuccess: Int,

        @Column(name = "scores_achieved")
        var scoresAchieved: Int

) : BaseEntity()