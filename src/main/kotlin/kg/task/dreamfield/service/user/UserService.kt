package kg.task.dreamfield.service.user

import kg.task.dreamfield.domain.user.User
import kg.task.dreamfield.exception.NotFoundException
import kg.task.dreamfield.repository.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UserService {
    fun findById(id: Long): User?
    fun getById(id: Long): User
    fun findByEmail(email: String): User?
    fun getByEmail(email: String): User
}

@Service
internal class DefaultUserService(
        private val userRepository: UserRepository
) : UserService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): User? {
        return userRepository.findByIdOrNull(id)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): User {
        return findById(id)
                ?: throw NotFoundException(User::class, id, "id")
    }

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    @Transactional(readOnly = true)
    override fun getByEmail(email: String): User {
        return findByEmail(email)
                ?: throw NotFoundException(User::class, email, "email")
    }

}