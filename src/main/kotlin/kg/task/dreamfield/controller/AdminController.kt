package kg.task.dreamfield.controller

import kg.task.dreamfield.dto.http.paging.PageResponseDto
import kg.task.dreamfield.dto.http.user.add.AddAdminUserRequestDto
import kg.task.dreamfield.dto.http.user.AdminDto
import kg.task.dreamfield.dto.http.user.paging.AdminSearchRequestDto
import kg.task.dreamfield.endpoint.user.AdminUserEndpoint
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/admins")
class AdminController(
        private val adminUserEndpoint: AdminUserEndpoint
) {

    @PostMapping("/create")
    fun create(@Valid @RequestBody requestDto: AddAdminUserRequestDto): AdminDto {
        return adminUserEndpoint.create(requestDto)
    }

    @PostMapping("/search")
    fun search(@Valid @RequestBody requestDto: AdminSearchRequestDto): PageResponseDto<AdminDto> {
        return adminUserEndpoint.search(requestDto)
    }

}