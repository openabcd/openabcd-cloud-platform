package io.github.openabcd.cloud.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Getter
@Builder
@RequiredArgsConstructor
public class Server {
    private final UUID id;
    private final String name;
    private final ServerStatus status;
}
