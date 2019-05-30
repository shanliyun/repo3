package com.wynlink.park_platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.wynlink.park_platform.websocket.SocketHandler;
import com.wynlink.park_platform.websocket.WebSocketInterceptor;



/**
 * WebSocketConfig
 * @author jzhang
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandler(), "/crgl")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }
}
