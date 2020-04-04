package kg.task.dreamfield.service.user

import com.querydsl.core.BooleanBuilder
import kg.task.dreamfield.domain.user.Admin
import kg.task.dreamfield.domain.user.QAdmin.admin
import kg.task.dreamfield.domain.user.paging.AdminFilterRequest
import kg.task.dreamfield.domain.user.paging.AdminSearchRequest
import kg.task.dreamfield.domain.user.request.AddAdminUserRequest
import kg.task.dreamfield.exception.NotFoundException
import kg.task.dreamfield.repository.findAll
import kg.task.dreamfield.repository.user.AdminUserRepository
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface AdminUserService {
    fun findById(id: Long): Admin?
    fun getById(id: Long): Admin
    fun findByEmail(email: String): Admin?
    fun getByEmail(email: String): Admin
    fun create(request: AddAdminUserRequest): Admin
    fun search(request: AdminSearchRequest): Page<Admin>
}


@Service
internal class DefaultAdminUserService(
        private val adminUserRepository: AdminUserRepository,
        private val passwordEncoder: PasswordEncoder
) : AdminUserService {
    @Transactional(readOnly = true)
    override fun findById(id: Long): Admin? {
        return adminUserRepository.findByIdOrNull(id)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): Admin {
        return findById(id)
                ?: throw NotFoundException(Admin::class, id, "id")
    }

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): Admin? {
        return adminUserRepository.findByEmail(email)
    }

    @Transactional(readOnly = true)
    override fun getByEmail(email: String): Admin {
        return findByEmail(email)
                ?: throw NotFoundException(Admin::class, email, "email")
    }

    @Transactional
    override fun create(request: AddAdminUserRequest): Admin {
        val admin = Admin(
                name = request.name,
                email = request.email,
                password = passwordEncoder.encode(request.password),
                role = request.role
        )

        return adminUserRepository.save(admin)
    }

    @Transactional(readOnly = true)
    override fun search(request: AdminSearchRequest): Page<Admin> {
        val predicate = BooleanBuilder()
        search(predicate, request.filter)

        return adminUserRepository.findAll(predicate, request.pageRequest)
    }

    private fun search(predicate: BooleanBuilder, filter: AdminFilterRequest) {
        filter.email?.let { predicate.and(admin.email.containsIgnoreCase(it)) }
        filter.name?.let { predicate.and(admin.name.containsIgnoreCase(it)) }
    }
}