package kg.task.dreamfield.repository.user

import kg.task.dreamfield.domain.user.User
import kg.task.dreamfield.repository.BaseRepository

interface UserRepository : BaseRepository<User> {
    fun findByEmail(email: String): User?
}