package com.adtec.sys.modules.sys.ws;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author lijunbin
 * @version 1.0
 * @date 2019-11-7 10:30
 */
@Configuration("globalWebsocketConfig")
@EnableWebSocket
@EnableWebSocketMessageBroker
public class GlobalWebsocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Value("${adminPath}")
    protected String adminPath;

    private final SysHandshakeInterceptor sysHandshakeInterceptor;
    private final WsHandshakeHandler wsHandshakeHandler;

    public GlobalWebsocketConfig(SysHandshakeInterceptor sysHandshakeInterceptor, WsHandshakeHandler wsHandshakeHandler) {
        this.sysHandshakeInterceptor = sysHandshakeInterceptor;
        this.wsHandshakeHandler = wsHandshakeHandler;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个 /ws 端点，前端通过这个端点进行连接
        registry.addEndpoint(adminPath + "/ws")
                .addInterceptors(sysHandshakeInterceptor)
                .setHandshakeHandler(wsHandshakeHandler)
                //解决跨域问题
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sys/topic", "/sys/queue", "/sys/exchange");
        //给指定用户发送消息的路径前缀，默认值是/user
        registry.setUserDestinationPrefix("/user/");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
