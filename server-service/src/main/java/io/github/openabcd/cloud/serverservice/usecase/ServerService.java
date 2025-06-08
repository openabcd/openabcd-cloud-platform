package io.github.openabcd.cloud.serverservice.usecase;

import io.github.openabcd.cloud.common.domain.Server;
import io.github.openabcd.cloud.common.component.mapper.ServerMapper;
import io.github.openabcd.cloud.grpc.ServerProto;
import io.github.openabcd.cloud.grpc.ServerServiceGrpc;
import io.github.openabcd.cloud.common.domain.ServerStatus;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.grpc.server.service.GrpcService;

import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class ServerService extends ServerServiceGrpc.ServerServiceImplBase {

    private final ServerMapper serverMapper;

    @Override
    public void getServers(ServerProto.GetServersRequest request,
            StreamObserver<ServerProto.ServerCollection> responseObserver) {
        log.info("getServers called with user_id: {}", request.getUserId());

        // Here you can use userId for authorization or filtering if needed
        val userId = request.getUserId();

        val collection = ServerProto.ServerCollection.newBuilder()
                .addAllServers(Stream.of(Server.builder().id(UUID.randomUUID()).name("Server1")
                        .status(ServerStatus.RUNNING).build()).map(serverMapper::toProto).toList()).build();

        responseObserver.onNext(collection);
        responseObserver.onCompleted();
    }

    @Override
    public void getServer(ServerProto.GetServerDocument request,
            StreamObserver<ServerProto.ServerDocument> responseObserver) {
        val server = Server.builder().id(UUID.fromString(request.getId())).name("Server1")
                .status(ServerStatus.RUNNING).build();

        responseObserver.onNext(serverMapper.toProto(server));
        responseObserver.onCompleted();
    }
}
