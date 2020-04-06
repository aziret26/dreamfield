package kg.task.dreamfield.domain.word

import kg.task.dreamfield.domain.BaseEntity
import kg.task.dreamfield.domain.user.Player
import javax.persistence.*

@Entity
@Table(name = "player_current_words")
class PlayerCurrentWord(

        @ManyToOne
        @JoinColumn(name = "player_id")
        val player: Player,

        @ManyToOne
        @JoinColumn(name = "word_id")
        val word: Word,

        @Column(name = "scores_available")
        var scoreAvailable: Int

) : BaseEntity()