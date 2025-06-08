package io.github.openabcd.cloud.serverservice.usecase;

import io.github.openabcd.cloud.common.domain.MainAccount;
import io.github.openabcd.cloud.common.domain.Server;
import io.github.openabcd.cloud.common.domain.ServerStatus;
import io.github.openabcd.cloud.common.vo.Passport;
import io.github.openabcd.cloud.common.vo.PassportHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @GetMapping("/servers")
    public List<Server> servers(@PassportHeader Passport passport) {
        val mainAccount = MainAccount.builder()
                .id(passport.userId())
                .name(passport.username())
                .build();

        val server1 = Server.builder()
                .id(UUID.randomUUID())
                .name("Server1")
                .status(ServerStatus.RUNNING)
                .mainAccount(mainAccount)
                .build();

        val server2 = Server.builder()
                .id(UUID.randomUUID())
                .name("Server2")
                .status(ServerStatus.STOPPED)
                .mainAccount(mainAccount)
                .build();

        return List.of(server1, server2);
    }


    @GetMapping("/servers/{serverId}")
    public Server getServer(@PassportHeader Passport passport, @PathVariable String serverId) {
        log.info("Get server with ID: {}, Passport: {}", serverId, passport);

        val mainAccount = MainAccount.builder()
                .id(passport.userId())
                .name(passport.username())
                .build();

        // For demonstration, returning a dummy server
        return Server.builder()
                .id(UUID.fromString(serverId))
                .name("Server" + serverId)
                .status(ServerStatus.RUNNING)
                .mainAccount(mainAccount)
                .build();
    }
}
