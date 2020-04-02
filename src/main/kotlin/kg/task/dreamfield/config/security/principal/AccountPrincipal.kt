package kg.task.dreamfield.config.security.principal

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

data class UserPrincipal(
        val email: String,

        @get:JvmName("getPassword_")
        val password: String,

        val permissions: Set<PermissionPrincipal>,

        override val role: RolePrincipal,

        override val id: Long

) : AccountPrincipal(email, password, permissions)
