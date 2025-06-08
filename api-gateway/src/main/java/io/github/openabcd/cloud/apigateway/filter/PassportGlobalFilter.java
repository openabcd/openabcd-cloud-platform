package io.github.openabcd.cloud.apigateway.filter;

import io.github.openabcd.cloud.common.JsonUtil;
import io.github.openabcd.cloud.common.vo.Passport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;


@Slf4j
@Component
public class PassportGlobalFilter implements GlobalFilter, Ordered {

    private static final Set<String> EXCLUDED_PATHS = Set.of("/siginin", "/signup"
    /// ..
    );
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final WebClient identityService;

    public PassportGlobalFilter(@Qualifier("identityService") WebClient identityService) {
        this.identityService = identityService;
    }

    /// 인증 헤더를 Passport로 변환하는 필터 <br/>
    ///
    /// @param exchange WebFlux exchange 요청 정보
    /// @param chain Gateway 필터 체인
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        val path = exchange.getRequest().getPath().value();
        if (EXCLUDED_PATHS.contains(path) || path.startsWith("/actuator")) {
            return chain.filter(exchange);
        }

        // PoC 이므로, userId를 Authorization 헤더에서 가져오는 것으로 가정
        val authorization = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);

        if (authorization == null) {
            return exchange.getResponse().setComplete().then(Mono.fromRunnable(() -> {
                exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FOUND);
                exchange.getResponse().getHeaders().set("Location", "/signin");
            }));
        }

        return identityService.post().uri("/passports")
                .header(AUTHORIZATION_HEADER, authorization)
                .retrieve()
                .bodyToMono(Passport.class)
                .flatMap(passport -> {
                    val json = JsonUtil.toJson(passport);
                    val base64EncodedJson = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));

                    val mutatedRequest = exchange.getRequest().mutate()
                            .header(Passport.PASSPORT_HEADER, base64EncodedJson)
                            .build();

                    val mutatedExchange = exchange.mutate()
                            .request(mutatedRequest)
                            .build();

                    return chain.filter(mutatedExchange);
                })
                .doOnError(throwable -> log.error(throwable.getMessage(), throwable));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
