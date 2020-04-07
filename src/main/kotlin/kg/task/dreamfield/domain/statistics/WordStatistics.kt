package kg.task.dreamfield.domain.statistics

import kg.task.dreamfield.domain.BaseEntity
import kg.task.dreamfield.domain.word.Word
import javax.persistence.*

@Entity
@Table(name = "word_statistics")
class WordStatistics(

        @Column(name = "unique_players")
        val uniquePlayers: Long,

        @Column(name = "attempts_success")
        val attemptsSuccess: Long,

        @Column(name = "attempts_failed")
        val attemptsFailed: Long,

        @Column(name = "attempts_total")
        val attemptsTotal: Long,

        @Column(name = "max_score_achieved")
        val maxScoreAchieved: Int,

        @OneToOne
        @JoinColumn(name = "word_id")
        val word: Word
) : BaseEntity()