package kg.task.dreamfield.controller

import kg.task.dreamfield.dto.http.account.CurrentAccountDto
import kg.task.dreamfield.endpoint.account.AccountEndpoint
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
        private val accountEndpoint: AccountEndpoint
) {

    @GetMapping("/me")
    fun me(): CurrentAccountDto {
        return accountEndpoint.getCurrentUser()
    }

}