package kg.task.dreamfield.service.user

import kg.task.dreamfield.config.security.principal.AccountPrincipal
import kg.task.dreamfield.config.security.principal.AdminPrincipal
import kg.task.dreamfield.config.security.principal.PlayerPrincipal
import kg.task.dreamfield.exception.UnauthorizedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

interface UserContextProvider {
    fun getCurrentAccount(): AccountPrincipal
    fun getCurrentUser(): AdminPrincipal
    fun getCurrentPlayer(): PlayerPrincipal
}

@Service
internal class DefaultUserContextProvider : UserContextProvider {
    override fun getCurrentAccount(): AccountPrincipal {
        return SecurityContextHolder.getContext().authentication.principal as AccountPrincipal?
                ?: throw UnauthorizedException("Failed to retrieve account principal")
    }

    override fun getCurrentUser(): AdminPrincipal {
        return getCurrentAccount() as? AdminPrincipal
                ?: throw UnauthorizedException("Failed to retrieve admin principal")
    }

    override fun getCurrentPlayer(): PlayerPrincipal {
        return getCurrentAccount() as? PlayerPrincipal
                ?: throw UnauthorizedException("Failed to retrieve player principal")
    }
}