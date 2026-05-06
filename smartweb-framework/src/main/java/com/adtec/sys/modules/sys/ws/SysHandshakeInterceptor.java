package com.adtec.sys.modules.sys.ws;

import com.adtec.sys.modules.sys.utils.UserUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * <p> </p>
 *
 * @author lijb
 * @since 2021-01-14
 */
@Component
public class SysHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("Before Handshake");
        //使用userName区分WebSocketHandler，以便定向发送消息(使用shiro获取session,或是使用上面的方式)
        String userName = UserUtils.getUser().getLoginName();
        if (userName == null) {
            userName = "default-system";
        }
        attributes.put("user", userName);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
