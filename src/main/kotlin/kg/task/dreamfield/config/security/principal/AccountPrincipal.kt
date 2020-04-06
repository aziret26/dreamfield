package kg.task.dreamfield.config.security.principal

import kg.task.dreamfield.domain.user.RoleCode
import org.springframework.security.core.userdetails.User
import java.io.Serializable

sealed class AccountPrincipal(
        email: String,
        password: String,
        permissions: Set<PermissionPrincipal>
) : User(email, password, permissions), Serializable {
    abstract val id: Long
    abstract val role: RolePrincipal
}

data class AdminPrincipal(
        override val id: Long,
        override val role: RolePrincipal,

        val email: String,

        @get:JvmName("getPassword_")
        val password: String,

        val permissions: Set<PermissionPrincipal> = setOf(PermissionPrincipal("ROLE_${RoleCode.ADMIN}", "ROLE_${RoleCode.ADMIN}"))

) : AccountPrincipal(email, password, permissions)


data class PlayerPrincipal(
        override val id: Long,
        override val role: RolePrincipal,
        val email: String,

        @get:JvmName("getPassword_")
        val password: String,

        val score: Long
) : AccountPrincipal(email, password, setOf(PermissionPrincipal("ROLE_${RoleCode.PLAYER}", "ROLE_${RoleCode.PLAYER}")))