package com.adtec.sys.modules.sys.ws;

import com.adtec.sys.modules.sys.security.SystemAuthorizingRealm;

import javax.security.auth.Subject;
import java.security.Principal;

public class WebsocketPrincipal implements Principal {

    private SystemAuthorizingRealm.Principal sysPrincipal;

    private final String name;

    public WebsocketPrincipal(String name) {
        this.name = name;
    }

    public WebsocketPrincipal(SystemAuthorizingRealm.Principal sysPrincipal) {
        this.sysPrincipal = sysPrincipal;
        this.name = this.sysPrincipal.getLoginName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    public SystemAuthorizingRealm.Principal getSysPrincipal() {
        return sysPrincipal;
    }
}
