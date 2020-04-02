package kg.task.dreamfield.repository.user

import kg.task.dreamfield.domain.user.Role
import kg.task.dreamfield.domain.user.RoleCode
import kg.task.dreamfield.repository.BaseRepository

interface RoleRepository : BaseRepository<Role> {
    fun getByCode(code: RoleCode): Role
}