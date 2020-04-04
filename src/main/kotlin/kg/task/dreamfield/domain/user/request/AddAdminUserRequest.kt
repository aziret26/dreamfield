package kg.task.dreamfield.domain.user.request

import kg.task.dreamfield.domain.user.Role

class AddAdminUserRequest(
        val email: String,
        val password: String,
        val name: String,
        val role: Role
)