package kg.task.dreamfield.config.security

import kg.task.dreamfield.config.security.principal.RolePrincipal
import kg.task.dreamfield.config.security.principal.UserPrincipal
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
        return userService.findByEmail(username).toPrincipal()
    }
}

private fun User.toPrincipal(): UserPrincipal = UserPrincipal(
        email = this.email,
        password = this.password,
        id = this.id!!,
        role = RolePrincipal(this.role.displayName),
        permissions = setOf()
)
