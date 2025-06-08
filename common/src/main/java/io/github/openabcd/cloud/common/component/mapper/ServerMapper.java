package io.github.openabcd.cloud.common.component.mapper;

import io.github.openabcd.cloud.common.domain.MainAccount;
import io.github.openabcd.cloud.common.domain.Server;
import io.github.openabcd.cloud.common.domain.ServerStatus;
import io.github.openabcd.cloud.grpc.ServerProto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ServerMapper {

    public Server toDomain(ServerProto.ServerDocument serverDocument) {
        return Server.builder()
                .id(UUID.fromString(serverDocument.getId()))
                .name(serverDocument.getName())
                .status(ServerStatus.valueOf(serverDocument.getStatus()))
                .mainAccount(MainAccount.builder().build()) // Assuming MainAccount is built with default values
                .build();
    }

    public ServerProto.ServerDocument toProto(Server server) {
        return ServerProto.ServerDocument.newBuilder()
                .setId(server.getId().toString())
                .setName(server.getName())
                .setStatus(server.getStatus().name())
                .build();
    }
}
