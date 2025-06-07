package io.github.openabcd.cloud.serverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "io.github.openabcd.cloud.serverservice",
        "io.github.openabcd.cloud.common"
})
public class ServerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerServiceApplication.class, args);
    }
}
