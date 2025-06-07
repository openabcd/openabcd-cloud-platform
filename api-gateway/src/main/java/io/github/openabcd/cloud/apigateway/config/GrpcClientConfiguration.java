package io.github.openabcd.cloud.apigateway.config;

import io.github.openabcd.cloud.grpc.ServerServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration(proxyBeanMethods = false)
public class GrpcClientConfiguration {

    @Bean
    public ServerServiceGrpc.ServerServiceFutureStub serverServiceBlockingStub(GrpcChannelFactory channels) {
        return ServerServiceGrpc.newFutureStub(channels.createChannel("server-service"));
    }
}
