package kg.task.dreamfield.dto.http.word

import kg.task.dreamfield.domain.word.WordStatus

data class UpdateWordStatusRequestDto (
        val status: WordStatus
)