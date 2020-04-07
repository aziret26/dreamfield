package kg.task.dreamfield.dto.http.word

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

data class AddWordRequestDto(
        @field:Pattern(regexp = "([а-я])+")
        val value: String,

        @field:Pattern(regexp = "([а-я])+")
        val description: String,

        @field:Max(1000)
        @field:Min(1)
        val maxScores: Int
)