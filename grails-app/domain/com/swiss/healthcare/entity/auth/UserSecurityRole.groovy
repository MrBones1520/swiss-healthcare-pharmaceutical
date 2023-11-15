package com.swiss.healthcare.entity.auth

import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.grails.datastore.gorm.GormEntity

@ToString(cache=true, includeNames=true, includePackage=false)
class UserSecurityRole implements GormEntity<UserSecurityRole>, Serializable{

    private static final long serialVersionUID = 23234234L

    User user
    SecurityRole securityRole

    static UserSecurityRole get(long userId, long securityRoleId) {
        criteriaFor(userId, securityRoleId).get()
    }

    static boolean exists(long userId, long securityRoleId) {
        criteriaFor(userId, securityRoleId).count()
    }

    private static DetachedCriteria criteriaFor(long userId, long securityRoleId) {
        where {
            user == User.load(userId) &&
                    securityRole == SecurityRole.load(securityRoleId)
        }
    }

    static UserSecurityRole create(User user, SecurityRole securityRole, boolean flush = false) {
        def instance = new UserSecurityRole(user: user, securityRole: securityRole)
        instance.save(flush: flush)
        instance
    }

    static boolean remove(User u, SecurityRole r) {
        if (u != null && r != null) {
            where { user == u && securityRole == r }.deleteAll()
        }
    }

    static int removeAll(User u) {
        u == null ? 0 : where { user == u }.deleteAll() as int
    }

    static int removeAll(SecurityRole r) {
        r == null ? 0 : where { securityRole == r }.deleteAll() as int
    }

    static constraints = {
        securityRole validator: { SecurityRole r, UserSecurityRole ur ->
            if (ur.user?.id) {
                withNewSession {
                    if (exists(ur.user.id, r.id))
                        return ['userRole.exists']
                }
            }
        }
    }

    static mapping = {
        id composite: ['user', 'securityRole']
        version false
    }

    @Override
    boolean equals(other) {
        if (other instanceof UserSecurityRole) {
            other.userId == user?.id && other.securityRoleId == securityRole?.id
        }
        false
    }

    @Override
    int hashCode() {
        def hashCode = new HashCodeBuilder()
        if (user) {
            hashCode.append(user.id)
        }
        if (securityRole) {
            hashCode.append(securityRole.id)
        }
        hashCode.toHashCode()
    }

}
