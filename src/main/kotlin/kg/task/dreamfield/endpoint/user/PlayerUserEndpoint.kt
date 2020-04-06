package kg.task.dreamfield.endpoint.user

import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.user.paging.PlayerSearchRequest
import kg.task.dreamfield.domain.user.request.AddPlayerUserRequest
import kg.task.dreamfield.domain.user.request.UpdatePlayerUserRequest
import kg.task.dreamfield.dto.http.paging.PageResponseDto
import kg.task.dreamfield.dto.http.user.add.AddPlayerUserRequestDto
import kg.task.dreamfield.dto.http.user.PlayerDto
import kg.task.dreamfield.dto.http.user.paging.PlayerSearchRequestDto
import kg.task.dreamfield.dto.http.user.update.UpdatePlayerUserRequestDto
import kg.task.dreamfield.mapper.paging.PageMapper
import kg.task.dreamfield.mapper.user.PlayerUserMapper
import kg.task.dreamfield.service.user.PlayerUserService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerUserEndpoint {
    fun create(requestDto: AddPlayerUserRequestDto): PlayerDto
    fun update(id: Long, requestDto: UpdatePlayerUserRequestDto): PlayerDto
    fun search(requestDto: PlayerSearchRequestDto): PageResponseDto<PlayerDto>
}

@Service
internal class DefaultPlayerUserEndpoint(
        private val playerUserService: PlayerUserService,
        private val playerUserMapper: PlayerUserMapper,
        private val pageMapper: PageMapper
) : PlayerUserEndpoint {

    @Transactional
    override fun create(requestDto: AddPlayerUserRequestDto): PlayerDto {
        val request: AddPlayerUserRequest = playerUserMapper.toAddPlayerUserRequest(requestDto)

        val player: Player = playerUserService.create(request)

        return playerUserMapper.toPlayerDto(player)
    }

    @Transactional
    override fun update(id: Long, requestDto: UpdatePlayerUserRequestDto): PlayerDto {
        val player: Player = playerUserService.getById(id)
        val request: UpdatePlayerUserRequest = playerUserMapper.toUpdatePlayerUserRequest(requestDto)

        playerUserService.update(player, request)

        return playerUserMapper.toPlayerDto(player)
    }

    @Transactional(readOnly = true)
    override fun search(requestDto: PlayerSearchRequestDto): PageResponseDto<PlayerDto> {
        val request: PlayerSearchRequest = playerUserMapper.toPlayerSearchRequest(requestDto)

        val page: Page<Player> = playerUserService.search(request)
        val playerDtos: Collection<PlayerDto> = page.content.map { playerUserMapper.toPlayerDto(it) }

        return pageMapper.toPageResponseDto(page, playerDtos)

    }

}