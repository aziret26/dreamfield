package kg.task.dreamfield.service.user

import com.querydsl.core.BooleanBuilder
import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.user.QPlayer.player
import kg.task.dreamfield.domain.user.paging.PlayerFilterRequest
import kg.task.dreamfield.domain.user.paging.PlayerSearchRequest
import kg.task.dreamfield.domain.user.request.AddPlayerUserRequest
import kg.task.dreamfield.domain.user.request.UpdatePlayerUserRequest
import kg.task.dreamfield.exception.NotFoundException
import kg.task.dreamfield.repository.findAll
import kg.task.dreamfield.repository.user.PlayerUserRepository
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PlayerUserService {
    fun findById(id: Long): Player?
    fun getById(id: Long): Player
    fun findByEmail(email: String): Player?
    fun getByEmail(email: String): Player
    fun create(request: AddPlayerUserRequest): Player
    fun update(player: Player, request: UpdatePlayerUserRequest): Player
    fun addScore(player: Player, scoreToAdd: Int): Player
    fun search(request: PlayerSearchRequest): Page<Player>
}

@Service
internal class DefaultPlayerUserService(
        private val playerUserRepository: PlayerUserRepository,
        private val passwordEncoder: PasswordEncoder
) : PlayerUserService {
    @Transactional(readOnly = true)
    override fun findById(id: Long): Player? {
        return playerUserRepository.findByIdOrNull(id)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): Player {
        return findById(id)
                ?: throw NotFoundException(Player::class, id, "id")
    }

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): Player? {
        return playerUserRepository.findByEmail(email)
    }

    @Transactional(readOnly = true)
    override fun getByEmail(email: String): Player {
        return findByEmail(email)
                ?: throw NotFoundException(Player::class, email, "email")
    }

    @Transactional
    override fun create(request: AddPlayerUserRequest): Player {
        val player = Player(
                name = request.name,
                email = request.email,
                password = passwordEncoder.encode(request.password),
                role = request.role,
                score = 0
        )

        return playerUserRepository.save(player)
    }

    @Transactional
    override fun update(player: Player, request: UpdatePlayerUserRequest): Player {
        return player.apply {
            name = request.name
            email = request.email

            playerUserRepository.save(this)
        }
    }

    override fun addScore(player: Player, scoreToAdd: Int): Player {
        return player.apply {
            score += scoreToAdd

            playerUserRepository.save(this)
        }
    }

    @Transactional(readOnly = true)
    override fun search(request: PlayerSearchRequest): Page<Player> {
        val predicate = BooleanBuilder()
        search(predicate, request.filter)

        return playerUserRepository.findAll(predicate, request.pageRequest)
    }

    private fun search(predicate: BooleanBuilder, filter: PlayerFilterRequest) {
        filter.email?.let { predicate.and(player.email.containsIgnoreCase(it)) }
        filter.name?.let { predicate.and(player.name.containsIgnoreCase(it)) }
    }
}