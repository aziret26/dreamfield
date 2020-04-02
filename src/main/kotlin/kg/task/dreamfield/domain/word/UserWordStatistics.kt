package kg.task.dreamfield.domain.word

import kg.task.dreamfield.domain.BaseEntity
import kg.task.dreamfield.domain.user.Player
import javax.persistence.*

@Entity
@Table(name = "user_word_statistics")
class UserWordStatistics(

        @ManyToOne
        @JoinTable(name = "user_id")
        val user: Player,

        @ManyToOne
        @JoinTable(name = "word_id")
        val word: Word,

        @Column(name = "best_tries_count")
        var bestTriesCount: Int,

        @Column(name = "attempts_count")
        var attemptsCount: Int,

        @Column(name = "scores_achieved")
        var scoresAchieved: Int,

        @Column(name = "is_finished")
        var isFinished: Boolean

) : BaseEntity()