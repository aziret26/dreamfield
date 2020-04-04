package kg.task.dreamfield.dto.http.user.add

import org.hibernate.validator.constraints.Length

data class AddAdminUserRequestDto(

        @field:Length(min = 1, max = 50)
        val name: String,

        @field:Length(min = 5, max = 50)
        val email: String,

        @field:Length(min = 3, max = 50)
        val password: String
)