package kg.task.dreamfield.domain.user

import kg.task.dreamfield.domain.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
sealed class User(

        @Column(name = "email", nullable = false)
        var email: String,

        @Column(name = "password", nullable = false)
        var password: String,

        @Column(name = "name", nullable = false)
        var name: String,

        @ManyToOne
        @JoinColumn(name = "role_id", nullable = false)
        var role: Role

) : BaseEntity()

@Entity
@Table(name = "users_admin")
class Admin(
        email: String,
        password: String,
        name: String,
        role: Role
) : User(email, password, name, role)

@Entity
@Table(name = "users_players")
class Player(
        email: String,
        password: String,
        name: String,
        role: Role,

        @Column(name = "score")
        var score: Long
) : User(email, password, name, role)