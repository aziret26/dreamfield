package kg.task.dreamfield.mapper.user

import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.user.RoleCode
import kg.task.dreamfield.domain.user.paging.PlayerFilterRequest
import kg.task.dreamfield.domain.user.paging.PlayerPageRequest
import kg.task.dreamfield.domain.user.paging.PlayerSearchRequest
import kg.task.dreamfield.domain.user.request.AddPlayerUserRequest
import kg.task.dreamfield.domain.user.request.UpdatePlayerUserRequest
import kg.task.dreamfield.dto.http.user.PlayerDto
import kg.task.dreamfield.dto.http.user.add.AddPlayerUserRequestDto
import kg.task.dreamfield.dto.http.user.paging.PlayerSearchRequestDto
import kg.task.dreamfield.dto.http.user.update.UpdatePlayerUserRequestDto
import kg.task.dreamfield.service.user.RoleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerUserMapper {
    fun toPlayerDto(player: Player): PlayerDto
    fun toAddPlayerUserRequest(requestDto: AddPlayerUserRequestDto): AddPlayerUserRequest
    fun toUpdatePlayerUserRequest(requestDto: UpdatePlayerUserRequestDto): UpdatePlayerUserRequest
    fun toPlayerSearchRequest(requestDto: PlayerSearchRequestDto): PlayerSearchRequest
}

@Service
internal class DefaultPlayerUserMapper(
        private val roleService: RoleService
) : PlayerUserMapper {

    @Transactional(readOnly = true)
    override fun toPlayerDto(player: Player): PlayerDto {
        return PlayerDto(
                id = player.id!!,
                email = player.email,
                name = player.name,
                score = player.score
        )
    }

    @Transactional(readOnly = true)
    override fun toAddPlayerUserRequest(requestDto: AddPlayerUserRequestDto): AddPlayerUserRequest {
        return AddPlayerUserRequest(
                name = requestDto.name,
                email = requestDto.email,
                password = requestDto.password,
                role = roleService.getByCode(RoleCode.PLAYER)
        )
    }

    override fun toUpdatePlayerUserRequest(requestDto: UpdatePlayerUserRequestDto): UpdatePlayerUserRequest {
        return UpdatePlayerUserRequest(
                name = requestDto.name,
                email = requestDto.email
        )
    }

    override fun toPlayerSearchRequest(requestDto: PlayerSearchRequestDto): PlayerSearchRequest {
        return PlayerSearchRequest(
                pageRequest = PlayerPageRequest(
                        pageInfo = PageInfo(
                                page = requestDto.pageRequest.page,
                                limit = requestDto.pageRequest.limit
                        ),
                        sortInfo = SortInfo(
                                sortBy = requestDto.sorting.sortBy,
                                sortDirection = requestDto.sorting.sortDirection
                        )
                ),
                filter = PlayerFilterRequest(
                        name = requestDto.filters?.name,
                        email = requestDto.filters?.email
                )
        )
    }
}