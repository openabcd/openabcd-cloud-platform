# 요구사항

- [ ] b3 span ID를 통해 트랜잭션 추적 (Istio 도입할 수도)
- [ ] Route 설정 형식을 Java 코드에서 YAML로 작성 (spring cloud config)
  - [ ] config yaml 제어할 수 있는 dashboard 제공
- [ ] BFF 로직은 controller에서 처리 (추후 BFF 분리 가능성 고려)

### vm options

```text
-Dotel.exporter.otlp.endpoint=http://localhost:4317
-Dotel.exporter.otlp.protocol=grpc
-Dotel.resource.attributes=service.name=api-gateway
-javaagent:opentelemetry-javaagent.jar
```
