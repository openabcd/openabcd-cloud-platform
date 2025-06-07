package io.github.openabcd.cloud.common.mapper;

import io.github.openabcd.cloud.common.domain.Server;
import io.github.openabcd.cloud.common.domain.ServerStatus;
import io.github.openabcd.cloud.grpc.ServerProto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ServerMapper {

    public Server toDomain(ServerProto.ServerDocument serverDocument) {
        return new Server(
                UUID.fromString(serverDocument.getId()),
                serverDocument.getName(),
                ServerStatus.valueOf(serverDocument.getStatus())
        );
    }

    public ServerProto.ServerDocument toProto(Server server) {
        return ServerProto.ServerDocument.newBuilder()
                .setId(server.getId().toString())
                .setName(server.getName())
                .setStatus(server.getStatus().name())
                .build();
    }
}
