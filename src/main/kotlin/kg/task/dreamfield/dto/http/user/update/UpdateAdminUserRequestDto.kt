package kg.task.dreamfield.dto.http.user.update

import org.hibernate.validator.constraints.Length

data class UpdateAdminUserRequestDto(

        @field:Length(min = 1, max = 100)
        val name: String,

        @field:Length(min = 5, max = 70)
        val email: String
)