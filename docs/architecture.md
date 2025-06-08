# Architecture

## v1

```text
┌──────────────────────┐
│  Client (Web/Mobile) │
└─────▲────────────────┘
      │ HTTP/JSON
┌─────┴────────────────┐
│ Spring Cloud Gateway │
└─────▲────────────────┘
      │ HTTP (REST)
┌─────┴────────────────┐
│ Microservice A       │◄──────gRPC──────┐
│ Microservice B       │◄──────gRPC──────┘
└──────────────────────┘
```

