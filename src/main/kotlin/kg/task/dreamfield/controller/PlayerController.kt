package kg.task.dreamfield.controller

import kg.task.dreamfield.dto.http.paging.PageResponseDto
import kg.task.dreamfield.dto.http.user.add.AddPlayerUserRequestDto
import kg.task.dreamfield.dto.http.user.PlayerDto
import kg.task.dreamfield.dto.http.user.paging.PlayerSearchRequestDto
import kg.task.dreamfield.dto.http.user.update.UpdatePlayerUserRequestDto
import kg.task.dreamfield.endpoint.user.PlayerUserEndpoint
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/players")
class PlayerController(
        private val playerUserEndpoint: PlayerUserEndpoint
) {

    @PostMapping("/create")
    fun createPlayer(@Valid @RequestBody requestDto: AddPlayerUserRequestDto): PlayerDto {
        return playerUserEndpoint.create(requestDto)
    }

    @PostMapping("/search")
    fun search(@Valid @RequestBody requestDto: PlayerSearchRequestDto): PageResponseDto<PlayerDto> {
        return playerUserEndpoint.search(requestDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long,
               @Valid @RequestBody requestDto: UpdatePlayerUserRequestDto): PlayerDto {
        return playerUserEndpoint.update(id, requestDto)
    }

}