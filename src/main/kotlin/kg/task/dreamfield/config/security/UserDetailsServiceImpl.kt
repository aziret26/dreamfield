package kg.task.dreamfield.config.security

import kg.task.dreamfield.config.security.principal.AccountPrincipal
import kg.task.dreamfield.config.security.principal.RolePrincipal
import kg.task.dreamfield.config.security.principal.AdminPrincipal
import kg.task.dreamfield.config.security.principal.PlayerPrincipal
import kg.task.dreamfield.domain.user.Admin
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.user.User
import kg.task.dreamfield.service.user.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserDetailsServiceImpl(
        private val userService: UserService
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        return userService.getByEmail(username).toPrincipal()
    }
}

private fun User.toPrincipal(): AccountPrincipal {
    return when (this) {
        is Admin -> toPrincipal()
        is Player -> toPrincipal()
    }
}

private fun Admin.toPrincipal(): AdminPrincipal = AdminPrincipal(
        email = this.email,
        password = this.password,
        id = this.id!!,
        role = RolePrincipal(this.role.code.toString()),
        permissions = setOf()
)

private fun Player.toPrincipal() = PlayerPrincipal(
        email = this.email,
        password = this.password,
        id = this.id!!,
        role = RolePrincipal(this.role.code.toString()),
        score = this.score
)