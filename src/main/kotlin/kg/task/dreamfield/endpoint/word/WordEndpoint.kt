package kg.task.dreamfield.endpoint.word

import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.domain.word.paging.WordSearchRequest
import kg.task.dreamfield.domain.word.request.AddWordRequest
import kg.task.dreamfield.dto.http.paging.PageResponseDto
import kg.task.dreamfield.dto.http.word.AddWordRequestDto
import kg.task.dreamfield.dto.http.word.UpdateWordStatusRequestDto
import kg.task.dreamfield.dto.http.word.WordDto
import kg.task.dreamfield.dto.http.word.paging.WordSearchRequestDto
import kg.task.dreamfield.mapper.paging.PageMapper
import kg.task.dreamfield.mapper.word.WordMapper
import kg.task.dreamfield.service.word.WordService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface WordEndpoint {
    fun create(requestDto: AddWordRequestDto): WordDto
    fun search(requestDto: WordSearchRequestDto): PageResponseDto<WordDto>
    fun updateStatus(id: Long, requestDto: UpdateWordStatusRequestDto): WordDto
}

@Service
internal class DefaultWordEndpoint(
        private val wordService: WordService,
        private val wordMapper: WordMapper,
        private val pageMapper: PageMapper
) : WordEndpoint {

    @Transactional
    override fun create(requestDto: AddWordRequestDto): WordDto {
        val request: AddWordRequest = wordMapper.toAddWordRequest(requestDto)

        val word: Word = wordService.create(request)

        return wordMapper.toWordDto(word)
    }

    @Transactional(readOnly = true)
    override fun search(requestDto: WordSearchRequestDto): PageResponseDto<WordDto> {
        val request: WordSearchRequest = wordMapper.toWordSearchRequest(requestDto, true)
        val page: Page<Word> = wordService.search(request)

        val wordDtos: Collection<WordDto> = page.content.map { wordMapper.toWordDto(it) }

        return pageMapper.toPageResponseDto(page, wordDtos)
    }

    @Transactional
    override fun updateStatus(id: Long, requestDto: UpdateWordStatusRequestDto): WordDto {
        val word: Word = wordService.getById(id)

        wordService.updateStatus(word, requestDto.status)

        return wordMapper.toWordDto(word)
    }


}