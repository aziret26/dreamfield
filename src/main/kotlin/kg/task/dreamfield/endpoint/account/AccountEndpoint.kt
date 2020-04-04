package kg.task.dreamfield.endpoint.account

import kg.task.dreamfield.domain.user.Admin
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.user.User
import kg.task.dreamfield.dto.http.account.CurrentAccountDto
import kg.task.dreamfield.dto.http.account.CurrentAdminDto
import kg.task.dreamfield.dto.http.account.CurrentPlayerDto
import kg.task.dreamfield.service.user.UserContextProvider
import kg.task.dreamfield.service.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface AccountEndpoint {
    fun getCurrentUser(): CurrentAccountDto
}

@Service
internal class DefaultAccountEndpoint(
        private val userService: UserService,
        private val userContextProvider: UserContextProvider
) : AccountEndpoint {

    @Transactional(readOnly = true)
    override fun getCurrentUser(): CurrentAccountDto {
        val currentPrincipal = userContextProvider.getCurrentAccount()

        return userService.getById(currentPrincipal.id).toCurrentAccountDto()
    }

}

private fun User.toCurrentAccountDto(): CurrentAccountDto = when (this) {
    is Admin -> toCurrentAdminDto()
    is Player -> toCurrentPlayerDto()
}

private fun Admin.toCurrentAdminDto() = CurrentAdminDto(
        id = this.id!!,
        name = this.name,
        email = this.email
)

private fun Player.toCurrentPlayerDto() = CurrentPlayerDto(
        id = this.id!!,
        name = this.name,
        email = this.email
)