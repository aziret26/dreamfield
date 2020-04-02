package kg.task.dreamfield.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.http.HttpMethod.*
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableAsync
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(* arrayOf(GET, POST, PUT, DELETE, PATCH, HEAD).map { it.name }.toTypedArray())
                .allowCredentials(true)
    }
}