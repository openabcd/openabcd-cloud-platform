package io.github.openabcd.cloud.apigateway.bff;

import io.github.openabcd.cloud.common.CompletableUtil;
import io.github.openabcd.cloud.common.domain.Server;
import io.github.openabcd.cloud.common.mapper.ServerMapper;
import io.github.openabcd.cloud.grpc.ServerProto;
import io.github.openabcd.cloud.grpc.ServerServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServerBff {

    private final ServerServiceGrpc.ServerServiceFutureStub serverGrpc;
    private final ServerMapper serverMapper;

    @GetMapping("/servers")
    public Mono<List<Server>> getServers(@RequestHeader("Openabcd-User-Id") String userId) {
        val request = ServerProto.GetServersRequest.newBuilder()
                .setUserId(userId)
                .build();

        log.info("Fetching servers for user: {}", userId);

        return Mono.fromFuture(CompletableUtil.toCompletableFuture(serverGrpc.getServers(request)))
                .map(ServerProto.ServerCollection::getServersList)
                .map(list -> list.stream()
                        .map(serverMapper::toDomain)
                        .toList());
    }

    @GetMapping("/servers/{serverId}")
    public Mono<Server> getServer(@RequestHeader("Openabcd-User-Id") String userId, @PathVariable String serverId) {
        val request = ServerProto.GetServerDocument.newBuilder()
                .setId(serverId)
                .setUserId(userId)
                .build();

        log.info("Fetching server with ID: {} for user: {}", serverId, userId);

        return Mono.fromFuture(CompletableUtil.toCompletableFuture(serverGrpc.getServer(request)))
                .map(serverMapper::toDomain);
    }
}
