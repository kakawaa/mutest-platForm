package com.mutest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/3/27 17:35
 * @description websocket配置
 * @modify
 */
@Configuration

public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
