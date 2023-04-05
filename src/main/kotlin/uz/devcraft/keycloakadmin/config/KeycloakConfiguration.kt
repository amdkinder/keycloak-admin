package uz.devcraft.keycloakadmin.config

import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.RealmResource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
@Component
class KeycloakConfiguration {
    @Value("\${kc.url}")
    var url: String? = null

    @Value("\${kc.realm}")
    var realm: String? = null

    @Value("\${kc.client-id}")
    var clientId: String? = null

    @Value("\${kc.client-secret}")
    var clientSecret: String? = null

    @Value("\${kc.username}")
    var username: String? = null

    @Value("\${kc.password}")
    var password: String? = null

    @Bean
    @Qualifier("keycloak")
    fun keycloak(): Keycloak {
        return KeycloakBuilder.builder()
            .serverUrl(url)
            .realm(realm)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .username(username)
            .password(password)
            .build()
    }

    @Bean
    fun realmResource(keycloak: Keycloak): RealmResource {
        return keycloak.realm(realm)
    }
}
