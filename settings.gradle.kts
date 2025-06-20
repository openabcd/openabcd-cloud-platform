rootProject.name = "openabcd-cloud"

include(
    // API Gateway
    "api-gateway",

    // MS
    "floating-ip-service",
    "identity-service",
    "server-service",
    "storage-service",

    // Libraries
    "common",
    "infra",

    // Platform BOM
    "platform-bom",
)
