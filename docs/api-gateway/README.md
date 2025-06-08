# API Gateway

## 용어집 (glossary)

- 경로(route):
  게이트웨이의 기본 구성 요소입니다. ID, 대상 URI, 조건자 컬렉션 및 필터 컬렉션으로 정의됩니다.
  집계 조건자가 true인 경우 경로가 일치합니다.
- Predicate:
  Java 8 Function Predicate입니다. 입력 유형은 Spring Framework ServerWebExchange입니다.
  이렇게 하면 헤더 또는 매개 변수와 같은 HTTP 요청의 모든 항목을 일치시킬 수 있습니다.
