package kg.task.dreamfield.endpoint.user

import kg.task.dreamfield.domain.user.Admin
import kg.task.dreamfield.domain.user.paging.AdminSearchRequest
import kg.task.dreamfield.domain.user.request.AddAdminUserRequest
import kg.task.dreamfield.domain.user.request.UpdateAdminUserRequest
import kg.task.dreamfield.dto.http.paging.PageResponseDto
import kg.task.dreamfield.dto.http.user.add.AddAdminUserRequestDto
import kg.task.dreamfield.dto.http.user.AdminDto
import kg.task.dreamfield.dto.http.user.paging.AdminSearchRequestDto
import kg.task.dreamfield.dto.http.user.update.UpdateAdminUserRequestDto
import kg.task.dreamfield.mapper.paging.PageMapper
import kg.task.dreamfield.mapper.user.AdminUserMapper
import kg.task.dreamfield.service.user.AdminUserService
import org.hibernate.sql.Update
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface AdminUserEndpoint {
    fun create(requestDto: AddAdminUserRequestDto): AdminDto
    fun update(id: Long, requestDto: UpdateAdminUserRequestDto): AdminDto
    fun search(requestDto: AdminSearchRequestDto): PageResponseDto<AdminDto>
}

@Service
internal class DefaultAdminUserEndpoint(
        private val adminUserService: AdminUserService,
        private val adminUserMapper: AdminUserMapper,
        private val pageMapper: PageMapper
) : AdminUserEndpoint {

    @Transactional
    override fun create(requestDto: AddAdminUserRequestDto): AdminDto {
        val request: AddAdminUserRequest = adminUserMapper.toAddAdminUserRequest(requestDto)

        val adminUser: Admin = adminUserService.create(request)

        return adminUserMapper.toAdminDto(adminUser)
    }

    @Transactional
    override fun update(id: Long, requestDto: UpdateAdminUserRequestDto): AdminDto {
        val admin: Admin = adminUserService.getById(id)
        val request: UpdateAdminUserRequest = adminUserMapper.toUpdateAdminUserRequest(requestDto)

        adminUserService.update(admin, request)

        return adminUserMapper.toAdminDto(admin)
    }

    @Transactional(readOnly = true)
    override fun search(requestDto: AdminSearchRequestDto): PageResponseDto<AdminDto> {
        val request: AdminSearchRequest = adminUserMapper.toAdminSearchRequest(requestDto)

        val page: Page<Admin> = adminUserService.search(request)
        val adminDtos: Collection<AdminDto> = page.content.map { adminUserMapper.toAdminDto(it) }

        return pageMapper.toPageResponseDto(page, adminDtos)
    }


}