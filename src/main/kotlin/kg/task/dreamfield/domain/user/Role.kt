package kg.task.dreamfield.domain.user

import kg.task.dreamfield.domain.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "roles")
class Role(
        @Column(name = "display_name", nullable = false)
        var displayName: String,

        @Enumerated(value = EnumType.STRING)
        @Column(name = "code", nullable = false, updatable = false)
        var code: RoleCode
) : BaseEntity()

enum class RoleCode {
    ADMIN,
    PLAYER
}