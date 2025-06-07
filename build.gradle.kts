plugins {
    java
    id("org.springframework.boot") version "3.5.0" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("com.google.protobuf") version "0.9.4" apply false
}

allprojects {
    group = "io.github.openabcd.cloud"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    plugins.withType<JavaPlugin> {
        the<JavaPluginExtension>().toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
        
        // lombok 사용하는 프로젝트에 compileOnly와 annotationProcessor 설정 연결
        configurations {
            compileOnly.configure {
                extendsFrom(configurations.findByName("annotationProcessor") ?: configurations.create("annotationProcessor"))
            }
        }
    }
}

extra["protoc"] = "4.30.2" // spring-grpc-dependencies:0.8.0 bom
extra["gen-grpc-java"] = "1.73.0"
extra["lombok"] = "1.18.38" // 모든 프로젝트에서 사용할 lombok 버전
