plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    // 내부 플랫폼/모듈
    implementation(platform(project(":platform-bom")))
    implementation(project(":common"))
    implementation(project(":infra"))

    // Spring Starters
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // gRPC
    implementation("io.grpc:grpc-services")
    implementation("org.springframework.grpc:spring-grpc-server-web-spring-boot-starter")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}
