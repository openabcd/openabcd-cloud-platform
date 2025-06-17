package io.github.openabcd.cloud.serverservice.apm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServerApmRepository extends JpaRepository<Server, UUID> {
}
