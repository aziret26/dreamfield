package kg.task.dreamfield.config.security.principal

import org.springframework.security.core.GrantedAuthority
import java.io.Serializable

data class PermissionPrincipal(
        val name: String,
        val code: String
) : GrantedAuthority, Serializable {

    override fun getAuthority(): String = code
}