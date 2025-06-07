rootProject.name = "openabcd-cloud-platform"

include(
    // API Gateway
    "api-gateway",

    // MS
    "floating-ip-service",
    "server-service",
    "storage-service",

    // Libraries
    "common",
    "infra",

    // Platform BOM
    "platform-bom",
)
