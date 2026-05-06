package com.adtec.sys.modules.sys.ws;

import com.adtec.sys.common.utils.IdGen;
import com.adtec.sys.modules.sys.security.SystemAuthorizingRealm;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;

/**
 * <p> </p>
 *
 * @author lijb
 * @since 2021-01-14
 */
@Component
public class WsHandshakeHandler extends DefaultHandshakeHandler {

    private final static Logger logger = LoggerFactory.getLogger(WsHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        WebsocketPrincipal websocketPrincipal;
        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
        if (null != principal) {
            logger.info(MessageFormat.format("WebSocket连接开始创建Principal，用户：{0}", principal.getName()));
            websocketPrincipal = new WebsocketPrincipal(principal);
        } else {
            websocketPrincipal = new WebsocketPrincipal("匿名用户-" + IdGen.uuid());
        }
        return websocketPrincipal;
    }
}
