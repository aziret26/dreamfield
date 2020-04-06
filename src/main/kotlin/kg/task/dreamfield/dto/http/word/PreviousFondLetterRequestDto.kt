package kg.task.dreamfield.dto.http.word

import javax.validation.constraints.Pattern

data class PreviousFondLetterRequestDto(
        @field:Pattern(regexp = "[а-я]")
        val letter: Char
)