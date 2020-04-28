package com.avira.iot.interview.common.config;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@ConditionalOnProperty("server.servlet.context-path")
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ContextPathRemappingWebFilter implements WebFilter {
    private final ServerProperties serverProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        val request = serverWebExchange.getRequest();
        val path = request.getURI().getPath();
        val contextPath = serverProperties.getServlet().getContextPath();
        if (path.startsWith(contextPath)) {
            return webFilterChain.filter(
                    serverWebExchange.mutate()
                            .request(request.mutate().contextPath(contextPath).build())
                            .build());
        }

        return webFilterChain.filter(serverWebExchange);
    }
}
