package kg.task.dreamfield.mapper.word

import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.domain.word.WordStatus
import kg.task.dreamfield.domain.word.paging.WordFilterRequest
import kg.task.dreamfield.domain.word.paging.WordPageRequest
import kg.task.dreamfield.domain.word.paging.WordSearchRequest
import kg.task.dreamfield.domain.word.request.AddWordRequest
import kg.task.dreamfield.dto.http.word.AddWordRequestDto
import kg.task.dreamfield.dto.http.word.WordDto
import kg.task.dreamfield.dto.http.word.paging.WordSearchRequestDto
import org.springframework.stereotype.Service

interface WordMapper {
    fun toWordDto(word: Word): WordDto
    fun toAddWordRequest(requestDto: AddWordRequestDto): AddWordRequest
    fun toWordSearchRequest(requestDto: WordSearchRequestDto, includeHidden: Boolean = false): WordSearchRequest
}

@Service
internal class DefaultWordMapper : WordMapper {

    override fun toWordDto(word: Word): WordDto {
        return WordDto(
                id = word.id!!,
                value = word.value,
                description = word.description,
                maxScores = word.maxScores,
                status = word.status
        )
    }

    override fun toAddWordRequest(requestDto: AddWordRequestDto): AddWordRequest {
        return AddWordRequest(
                value = requestDto.value,
                description = requestDto.description,
                maxScores = requestDto.maxScores
        )
    }

    override fun toWordSearchRequest(requestDto: WordSearchRequestDto, includeHidden: Boolean): WordSearchRequest {
        val status: WordStatus? = when (includeHidden) {
            true -> requestDto.filters?.status
            false -> WordStatus.VISIBLE
        }

        return WordSearchRequest(
                pageRequest = WordPageRequest(
                        pageInfo = PageInfo(
                                page = requestDto.pageRequest.page,
                                limit = requestDto.pageRequest.limit
                        ),
                        sortInfo = SortInfo(
                                sortBy = requestDto.sorting.sortBy,
                                sortDirection = requestDto.sorting.sortDirection
                        )
                ),
                filter = WordFilterRequest(
                        value = requestDto.filters?.value,
                        status = status
                )
        )
    }
}