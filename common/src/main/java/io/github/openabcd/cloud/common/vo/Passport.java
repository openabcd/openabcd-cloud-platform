package io.github.openabcd.cloud.common.vo;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Passport(
        UUID userId,
        String username,
        UUID projectId
) {
    public static final String PASSPORT_HEADER = "Openabcd-Passport";
}
