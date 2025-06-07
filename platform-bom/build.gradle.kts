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

    // Spring GRPC BOM import
    api(platform("org.springframework.grpc:spring-grpc-dependencies:0.8.0"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
        }
    }
}
