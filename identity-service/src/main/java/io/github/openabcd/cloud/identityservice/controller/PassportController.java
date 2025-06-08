package io.github.openabcd.cloud.identityservice.controller;

import io.github.openabcd.cloud.common.vo.Passport;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PassportController {

    /// PoC <br/>
    /// 추후 실질적인 검증후 MS에서 사용할 Passport 발급
    @PostMapping("/passports")
    public Passport issue(@RequestHeader("Authorization") String authorization) {
        // 실제로는 JWT 토큰을 파싱해야 함 + 유효성 검증
        val userId = authorization;

        return Passport.builder()
                .userId(UUID.fromString(userId))
                .username("testUser1")
                .projectId(UUID.randomUUID())
                .build();
    }
}
