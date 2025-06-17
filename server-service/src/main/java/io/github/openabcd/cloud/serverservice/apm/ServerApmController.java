package io.github.openabcd.cloud.serverservice.apm;

import com.github.f4b6a3.uuid.UuidCreator;
import io.github.openabcd.cloud.common.vo.Passport;
import io.github.openabcd.cloud.common.vo.PassportHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/server-apm")
@RequiredArgsConstructor
public class ServerApmController {

    private final ServerApmService serverApmService;

    @PostMapping
    @TraceArgument
    public ResponseEntity<CreateServer.Response> createServer(@PassportHeader Passport passport, @RequestBody CreateServer.Request request) {
        log.info("Creating server with name: {}, Passport: {}", request.name(), passport);
        val server = serverApmService.createServer(new Server(UuidCreator.getTimeOrderedEpoch(), request.name()));
        return ResponseEntity.ok(new CreateServer.Response(server.getId(), server.getName()));
    }

    record CreateServer() {

        public record Request(String name) {
        }

        public record Response(UUID id, String name) {
        }
    }
}
