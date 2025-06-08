package io.github.openabcd.cloud.common.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MainAccount {
    private UUID id;
    private String name;
}
