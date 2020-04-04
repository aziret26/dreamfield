package kg.task.dreamfield.domain.word

import kg.task.dreamfield.domain.BaseEntity
import kg.task.dreamfield.domain.user.Player
import javax.persistence.*

@Entity
@Table(name = "user_current_words")
class PlayerCurrentWord(

        @ManyToOne
        @JoinColumn(name = "player_id")
        val player: Player,

        @ManyToOne
        @JoinColumn(name = "word_id")
        val word: Word,

        @Column(name = "tries_count")
        var triesCount: Int,

        @Column(name = "is_last_try")
        var isLastTry: Boolean

) : BaseEntity()