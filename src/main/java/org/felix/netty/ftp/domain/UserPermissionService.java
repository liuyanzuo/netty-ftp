package org.felix.netty.ftp.domain;

import org.felix.netty.ftp.utils.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * user permission service:
 * - check user login state
 * - check other
 */
public class UserPermissionService {

    private static final Logger LOG = LoggerFactory.getLogger(UserPermissionService.class);

    public static boolean checkLogged(String user, String psw) {
        if ("anonymous".equals(user)) {
            LOG.debug("[user permission]-[anonymous]");
            return true;
        }
        if (isWhiteUser(user)) {
            LOG.debug("[user permission]-[white user]");
            return true;
        }
        LOG.debug("[user permission]-[login failed]");
        return false;
    }

    public static boolean isWhiteUser(String user) {
        return ServerConfig.getWhiteList().contains(user);
    }
}
