package kg.task.dreamfield.domain.word

import kg.task.dreamfield.domain.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "words")
class Word(

        @Column(name = "value", nullable = false)
        var value: String,

        @Column(name = "description")
        var description: String,

        @Column(name = "max_scores", nullable = false)
        var maxScores: Int,

        @Enumerated(value = EnumType.STRING)
        @Column(name = "status", nullable = false)
        var status: WordStatus

) : BaseEntity()

