package kg.task.dreamfield.dto.http.account

sealed class CurrentAccountDto {
    abstract val id: Long
    abstract val name: String
    abstract val email: String
    abstract val type: AccountType
}

class CurrentAdminDto(
        override val id: Long,
        override val name: String,
        override val email: String
) : CurrentAccountDto() {
    override val type: AccountType = AccountType.ADMIN
}

class CurrentPlayerDto(
        override val id: Long,
        override val name: String,
        override val email: String
) : CurrentAccountDto() {
    override val type: AccountType = AccountType.PLAYER
}

enum class AccountType {
    ADMIN,
    PLAYER
}
