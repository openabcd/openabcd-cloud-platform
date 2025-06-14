package io.github.openabcd.cloud.common.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;


@Getter
@Builder
public class Server {
    private UUID id;
    private final String name;
    private final ServerStatus status;

    /// 연관 자원
    private final MainAccount mainAccount;
}
