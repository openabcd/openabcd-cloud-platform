import com.google.protobuf.gradle.id
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.withType
import org.gradle.api.plugins.ExtensionAware
import net.ltgt.gradle.errorprone.ErrorProneOptions
import net.ltgt.gradle.errorprone.errorprone

plugins {
    `java-library`
    id("com.google.protobuf")
    id("net.ltgt.errorprone")
}

dependencies {
    // platform-bom에서 모든 의존성 버전 관리
    implementation(platform(project(":platform-bom")))

    // Protobuf and gRPC dependencies
    implementation("io.grpc:grpc-services")

    /// api-gateway 에는 해당 의존성이 필요없는데 가지고 있다 보니 -> main.web-application-type: reactive 을 추가해야한느 문제가 발생함
    implementation("org.springframework.grpc:spring-grpc-server-web-spring-boot-starter")

    // lombok - 명시적 버전 지정
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    // jspecify
    compileOnly("org.jspecify:jspecify:1.0.0")
    annotationProcessor("org.jspecify:jspecify:1.0.0")

    errorprone("com.uber.nullaway:nullaway:0.12.6")
    errorprone("com.google.errorprone:error_prone_core:2.37.0")
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

tasks.withType<JavaCompile>().configureEach {
    val errorproneOptions = (options as ExtensionAware)
        .extensions.getByName("errorprone") as ErrorProneOptions

    errorproneOptions.disableAllChecks.set(true)
    errorproneOptions.option("NullAway:OnlyNullMarked", "true")
    errorproneOptions.error("NullAway")
}

tasks.compileJava {
    options.errorprone.error("NullAway")
}


// Lombok 설정
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

