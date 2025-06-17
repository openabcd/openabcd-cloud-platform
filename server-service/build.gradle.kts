import net.ltgt.gradle.errorprone.errorprone

plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("net.ltgt.errorprone")
}

dependencies {
    // 내부 플랫폼/모듈
    implementation(platform(project(":platform-bom")))
    implementation(project(":common"))
    implementation(project(":infra"))

    // Spring Starters
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // gRPC
    implementation("io.grpc:grpc-services")
    implementation("org.springframework.grpc:spring-grpc-server-web-spring-boot-starter")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("com.github.f4b6a3:uuid-creator")

    // docker-compose
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    implementation("io.opentelemetry:opentelemetry-api")
    implementation("io.opentelemetry:opentelemetry-context")

    // JSpecify
    implementation("org.jspecify:jspecify:1.0.0")
    errorprone("com.uber.nullaway:nullaway:0.12.6")
    errorprone("com.google.errorprone:error_prone_core:2.37.0")
}

tasks.withType(JavaCompile::class.java).configureEach {
    options.errorprone {
        disableAllChecks = true
        option("NullAway:OnlyNullMarked", "true")
        option("NullAway:CustomContractAnnotations", "org.springframework.lang.Contract")
    }
}

tasks.compileJava {
    options.errorprone.error("NullAway")
}

// lombok 설정
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}
