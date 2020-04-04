package kg.task.dreamfield.repository.user

import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.repository.BaseRepository

interface PlayerUserRepository : BaseRepository<Player> {
    fun findByEmail(email: String): Player
}