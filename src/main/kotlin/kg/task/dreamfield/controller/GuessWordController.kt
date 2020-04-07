package kg.task.dreamfield.controller

import kg.task.dreamfield.dto.http.word.GuessWordDto
import kg.task.dreamfield.dto.http.word.GuessWordRequestDto
import kg.task.dreamfield.endpoint.dto.guess.GuessResult
import kg.task.dreamfield.endpoint.word.GuessWordEndpoint
import kg.task.dreamfield.service.user.UserContextProvider
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/words/guess")
@PreAuthorize("hasRole('ROLE_PLAYER')")
class GuessWordController(
        private val guessWordEndpoint: GuessWordEndpoint,
        private val userContextProvider: UserContextProvider
) {

    @GetMapping("/next-guess-word")
    fun getNextGuessWord(): GuessWordDto {
        return guessWordEndpoint.getNextGuessWord(userContextProvider.getCurrentPlayer())
    }

    @PostMapping("/guess-word")
    fun guessWord(@Valid @RequestBody requestDto: GuessWordRequestDto): GuessWordDto {
        return guessWordEndpoint.guessWord(userContextProvider.getCurrentPlayer(), requestDto)
    }


}