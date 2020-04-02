package kg.task.dreamfield.domain.user

import kg.task.dreamfield.domain.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "users")
class User(

        @Column(name = "email", nullable = false)
        var email: String,

        @Column(name = "password", nullable = false)
        var password: String,

        @ManyToOne
        @JoinColumn(name = "role_id", nullable = false)
        var role: Role

) : BaseEntity()