package kg.task.dreamfield.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.session.SessionRegistry
import org.springframework.session.FindByIndexNameSessionRepository
import org.springframework.session.security.SpringSessionBackedSessionRegistry

@Configuration
class SessionConfig(
//        private val sessionRepository: FindByIndexNameSessionRepository<*>
) {

//    @Autowired
//    private lateinit var sessionRepository: FindByIndexNameSessionRepository<*>

//    @Bean
//    fun sessionRegistry(): SessionRegistry {
//        return SpringSessionBackedSessionRegistry(sessionRepository)
//    }

}