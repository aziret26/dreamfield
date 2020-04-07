package kg.task.dreamfield.domain.statistics

import kg.task.dreamfield.domain.BaseEntity
import kg.task.dreamfield.domain.user.Player
import javax.persistence.*

@Entity
@Table(name = "player_statistics")
class PlayerStatistics(

        @Column(name = "unique_words")
        val uniqueWords: Long,

        @Column(name = "attempts_success")
        val attemptsSuccess: Long,

        @Column(name = "attempts_failed")
        val attemptsFailed: Long,

        @Column(name = "attempts_total")
        val attemptsTotal: Long,

        @Column(name = "total_score")
        val scoresAchieved: Long,

        @OneToOne
        @JoinColumn(name = "player_id")
        val player: Player
) : BaseEntity()