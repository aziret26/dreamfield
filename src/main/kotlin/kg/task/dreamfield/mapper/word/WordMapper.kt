package kg.task.dreamfield.mapper.word

import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.word.WordStatus
import kg.task.dreamfield.domain.word.paging.WordFilterRequest
import kg.task.dreamfield.domain.word.paging.WordPageRequest
import kg.task.dreamfield.domain.word.paging.WordSearchRequest
import kg.task.dreamfield.dto.http.word.paging.WordSearchRequestDto
import org.springframework.stereotype.Service

interface WordMapper {
    fun toWordSearchRequest(requestDto: WordSearchRequestDto, includeHidden: Boolean = false): WordSearchRequest
}

@Service
internal class DefaultWordMapper : WordMapper {

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
                        searchParameter = requestDto.filters?.value,
                        status = status
                )
        )
    }
}