package kg.task.dreamfield.mapper.user

import kg.task.dreamfield.domain.paging.PageInfo
import kg.task.dreamfield.domain.paging.SortInfo
import kg.task.dreamfield.domain.user.Admin
import kg.task.dreamfield.domain.user.RoleCode
import kg.task.dreamfield.domain.user.paging.AdminFilterRequest
import kg.task.dreamfield.domain.user.paging.AdminPageRequest
import kg.task.dreamfield.domain.user.paging.AdminSearchRequest
import kg.task.dreamfield.domain.user.request.AddAdminUserRequest
import kg.task.dreamfield.domain.user.request.UpdateAdminUserRequest
import kg.task.dreamfield.dto.http.user.AdminDto
import kg.task.dreamfield.dto.http.user.add.AddAdminUserRequestDto
import kg.task.dreamfield.dto.http.user.paging.AdminSearchRequestDto
import kg.task.dreamfield.dto.http.user.update.UpdateAdminUserRequestDto
import kg.task.dreamfield.service.user.RoleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface AdminUserMapper {
    fun toAddAdminUserRequest(requestDto: AddAdminUserRequestDto): AddAdminUserRequest
    fun toUpdateAdminUserRequest(requestDto: UpdateAdminUserRequestDto): UpdateAdminUserRequest
    fun toAdminDto(admin: Admin): AdminDto
    fun toAdminSearchRequest(requestDto: AdminSearchRequestDto): AdminSearchRequest
}

@Service
internal class DefaultAdminUserMapper(
        private val roleService: RoleService
) : AdminUserMapper {

    @Transactional(readOnly = true)
    override fun toAddAdminUserRequest(requestDto: AddAdminUserRequestDto): AddAdminUserRequest {
        return AddAdminUserRequest(
                name = requestDto.name,
                email = requestDto.email,
                password = requestDto.password,
                role = roleService.getByCode(RoleCode.ADMIN)
        )
    }

    override fun toUpdateAdminUserRequest(requestDto: UpdateAdminUserRequestDto): UpdateAdminUserRequest {
        return UpdateAdminUserRequest(
                name = requestDto.name,
                email = requestDto.email
        )
    }

    @Transactional(readOnly = true)
    override fun toAdminDto(admin: Admin): AdminDto {
        return AdminDto(
                id = admin.id!!,
                email = admin.email,
                name = admin.name
        )
    }

    override fun toAdminSearchRequest(requestDto: AdminSearchRequestDto): AdminSearchRequest {
        return AdminSearchRequest(
                pageRequest = AdminPageRequest(
                        pageInfo = PageInfo(
                                page = requestDto.pageRequest.page,
                                limit = requestDto.pageRequest.limit
                        ),
                        sortInfo = SortInfo(
                                sortBy = requestDto.sorting.sortBy,
                                sortDirection = requestDto.sorting.sortDirection
                        )
                ),
                filter = AdminFilterRequest(
                        name = requestDto.filters?.name,
                        email = requestDto.filters?.email
                )
        )
    }

}