package io.github.openabcd.cloud.serverservice.apm;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServerApmService {
    private final ServerApmRepository serverApmRepository;

    @TraceArgument
    @Transactional
    public Server createServer(Server server) {
        return serverApmRepository.save(server);
    }
}
