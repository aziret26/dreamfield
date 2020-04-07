package kg.task.dreamfield.dto.http.word

import javax.validation.Valid
import javax.validation.constraints.Pattern

data class GuessWordRequestDto(
        @field:Pattern(regexp = "([а-я])+")
        val word: String,

        @Valid
        val foundLetters: List<PreviousFondLetterRequestDto>
)