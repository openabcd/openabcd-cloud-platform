### vm options

```text
-Dotel.exporter.otlp.endpoint=http://localhost:4317
-Dotel.exporter.otlp.protocol=grpc
-Dotel.resource.attributes=service.name=server-service
-Dotel.logs.exporter=otlp
-javaagent:opentelemetry-javaagent.jar
```
