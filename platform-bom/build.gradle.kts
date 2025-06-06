plugins {
    `java-platform`
    `maven-publish`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    // Spring Cloud BOM import
    api(platform("org.springframework.cloud:spring-cloud-dependencies:2025.0.0"))

    constraints {
        // OpenTelemetry
        api("io.opentelemetry.instrumentation:opentelemetry-logback-mdc-1.0:2.16.0-alpha")

        // 여기에 다른 공통 의존성을 추가할 수 있습니다
        // 예: api("org.projectlombok:lombok:1.18.30")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
        }
    }
}
