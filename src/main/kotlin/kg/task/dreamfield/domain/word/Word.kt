package kg.task.dreamfield.domain.word

import kg.task.dreamfield.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "words")
class Word(

        @Column(name = "value", nullable = false)
        var value: String,

        @Column(name = "max_scores", nullable = false)
        var maxScores: Int,

        @Column(name = "is_hidden")
        var isHidden: Boolean,

        @Column(name = "description")
        var description: String

) : BaseEntity()