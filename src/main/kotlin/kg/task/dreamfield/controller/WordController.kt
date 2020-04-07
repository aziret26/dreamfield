package kg.task.dreamfield.controller

import kg.task.dreamfield.dto.http.paging.PageResponseDto
import kg.task.dreamfield.dto.http.word.AddWordRequestDto
import kg.task.dreamfield.dto.http.word.UpdateWordStatusRequestDto
import kg.task.dreamfield.dto.http.word.WordDto
import kg.task.dreamfield.dto.http.word.paging.WordSearchRequestDto
import kg.task.dreamfield.endpoint.word.WordEndpoint
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/words")
@PreAuthorize("hasRole('ROLE_ADMIN')")
class WordController(
        private val wordEndpoint: WordEndpoint
) {

    @PostMapping()
    fun create(@Valid @RequestBody requestDto: AddWordRequestDto): WordDto {
        return wordEndpoint.create(requestDto)
    }

    @PostMapping("/search")
    fun search(@Valid @RequestBody requestDto: WordSearchRequestDto): PageResponseDto<WordDto> {
        return wordEndpoint.search(requestDto)
    }

    @PutMapping("/{id}/status")
    fun updateStatus(@PathVariable id: Long,
                     @Valid @RequestBody requestDto: UpdateWordStatusRequestDto): WordDto {
        return wordEndpoint.updateStatus(id, requestDto)
    }

}