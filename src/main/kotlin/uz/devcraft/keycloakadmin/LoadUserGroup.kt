package uz.devcraft.keycloakadmin

import org.keycloak.admin.client.resource.RealmResource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class LoadUserGroup(private val realmResource: RealmResource) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @PostConstruct
    fun addGroup() {
        val userGroupId = "65a7daf7-647d-4de8-a1d8-328095b12d92"
        val merchantGroup = "7e2632f4-dbbb-490c-abf6-8d2127e771e4"
//        val userGroupMembers = realmResource.groups().group(userGroupId).members()
        var merchantUsers = realmResource.users()
            .list()
            .filter {
                ((it.username.length == 9 || it.username.length == 14)) && isNumeric(it.username)
            }
            .filter {
                val userResource = realmResource.users().get(it.id)
                userResource.groups().none { group -> group.name == "MERCHANT_GROUP" }
            }
        log.info("merchant is not merchant group size: ${merchantUsers.size}")
        merchantUsers.forEach {
            val userResource = realmResource.users().get(it.id)
            userResource.joinGroup(merchantGroup)
        }
    }

    fun isNumeric(toCheck: String): Boolean {
        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        return toCheck.matches(regex)
    }
}
