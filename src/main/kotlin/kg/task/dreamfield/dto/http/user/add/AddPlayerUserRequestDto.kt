package kg.task.dreamfield.dto.http.user.add

import org.hibernate.validator.constraints.Length

data class AddPlayerUserRequestDto(

        @field:Length(min = 1, max = 100)
        val name: String,

        @field:Length(min = 5, max = 70)
        val email: String,

        @field:Length(min = 3, max = 255)
        val password: String
)