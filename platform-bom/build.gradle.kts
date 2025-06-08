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

    constraints {
        api("org.jspecify:jspecify:1.0.0")
        api("com.uber.nullaway:nullaway:0.12.6")
        api("com.google.errorprone:error_prone_core:2.37.0")
        api("org.projectlombok:lombok:1.18.38")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
        }
    }
}
