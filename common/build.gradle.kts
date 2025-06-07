import com.google.protobuf.gradle.id

plugins {
    `java-library`
    id("com.google.protobuf")
}

dependencies {
    // platform-bom에서 모든 의존성 버전 관리
    implementation(platform(project(":platform-bom")))

    // Protobuf and gRPC dependencies
    implementation("io.grpc:grpc-services")
    implementation("org.springframework.grpc:spring-grpc-server-web-spring-boot-starter")

    // lombok - 명시적 버전 지정
    compileOnly("org.projectlombok:lombok:${rootProject.extra["lombok"]}")
    annotationProcessor("org.projectlombok:lombok:${rootProject.extra["lombok"]}")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${rootProject.extra["protoc"]}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.extra["gen-grpc-java"]}"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc") {
                    option("jakarta_omit")
                    option("@generated=omit")
                }
            }
        }
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}
