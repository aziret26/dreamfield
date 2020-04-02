package kg.task.dreamfield.service.user

import kg.task.dreamfield.domain.user.Role
import kg.task.dreamfield.domain.user.RoleCode
import kg.task.dreamfield.exception.NotFoundException
import kg.task.dreamfield.repository.user.RoleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface RoleService {
    fun findById(id: Long): Role?
    fun getByById(id: Long): Role
    fun getByCode(code: RoleCode): Role
}

@Service
internal class DefaultRoleService(
        private val roleRepository: RoleRepository
) : RoleService {
    @Transactional(readOnly = true)
    override fun findById(id: Long): Role? {
        return roleRepository.findByIdOrNull(id)
    }

    @Transactional(readOnly = true)
    override fun getByById(id: Long): Role {
        return findById(id)
                ?: throw NotFoundException(Role::class, id, "id")
    }

    @Transactional(readOnly = true)
    override fun getByCode(code: RoleCode): Role {
        return roleRepository.getByCode(code)
    }

}