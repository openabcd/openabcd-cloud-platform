package io.github.openabcd.cloud.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServerStatus {
    RUNNING("실행 중"),
    STOPPED("중지됨"),
    DELETED("삭제됨");

    private final String description;
}
