## 구조

```text
my-cloud-platform/
├── build.gradle.kts       <-- 루트 build 파일
├── settings.gradle.kts    <-- 모듈 포함
├── common/                <-- 공통 유틸, DTO, 설정
│   └── build.gradle.kts
├── api-gateway/           <-- WebFlux + Spring Cloud Gateway
│   └── build.gradle.kts
├── bff-server-api/        <-- 서버 조회, UX 맞춤 응답
│   └── build.gradle.kts
├── server-service/        <-- 서버 생성/삭제 등 CUD 처리
│   └── build.gradle.kts
├── public-ip-service/     <-- Public IP 관리
│   └── build.gradle.kts
├── storage-service/       <-- Storage 관리
│   └── build.gradle.kts
└── infra/                 <-- DB, Kafka, OpenStack Client
    └── build.gradle.kts
```

## API 설계

| 메서드    | 경로               | 처리 위치                           | 설명                        |
|--------|------------------|---------------------------------|---------------------------|
| POST   | /v1/servers      | bff-server-api → server-service | 서버 생성 요청 위임               |
| DELETE | /v1/servers/{id} | bff-server-api → server-service | 서버 삭제 요청                  |
| GET    | /v1/servers/{id} | bff-server-api                  | server, ip, storage 조합 응답 |
| GET    | /v1/servers      | bff-server-api                  | 검색, 필터 등 사용자 View 맞춤      |

## ✅ 개념 비교: API Gateway vs BFF

| 항목 | API Gateway                                 | BFF (Backend For Frontend)               |
|----|---------------------------------------------|------------------------------------------|
| 역할 | 트래픽 분산, 인증, 라우팅, 로깅, 보안 등 인프라 관점            | 프론트엔드에 맞는 응답 조합, 변형 등 UX/도메인 관점          |
| 위치 | 시스템 입구 (Edge)                               | 일반적으로 Gateway 뒤에 존재                      |
| 기능 | 인증/인가, 요청 라우팅, Rate limiting, Metrics, CORS | 복합 데이터 조합, View 최적화, Client-specific API |

```text
[Client]
   ↓
[API Gateway (공통)]
   ↓
 ┌──────────────────────────────┐
 │ 서버-BFF   플로팅-IP-BFF   스토리지-BFF │   ← 도메인별 BFF(UI에 최적화된 응답을 조합)
 └──────────────────────────────┘
   ↓              ↓            ↓
[서버 MS]     [플로팅 IP MS]    [스토리지 MS]
```

다음과 같은 구조에서 API Gateway의 부하를 나눠서 처리하면 좋지만, 관리 포인트가 늘어나기에

현 시점에서는 API Gateway에서 BFF 역할을 함께 수행하는 구조로 설계합니다.


## 내부 통신 (HTTP vs gRPC)

| 항목          | HTTP (REST)              | gRPC                        |
|-------------|--------------------------|-----------------------------|
| 프로토콜        | HTTP/1.1                 | HTTP/2                      |
| 메시지 포맷      | JSON                     | Protobuf (바이너리)             |
| 성능          | 느림 (텍스트 기반, 파싱 비용 존재)    | 빠름 (바이너리 전송, 효율적 직렬화/역직렬화)  |
| 데이터 크기      | 큼                        | 작음                          |
| 스트리밍 지원     | 제한적 (SSE, WebSocket 필요)  | 양방향 스트리밍 지원                 |
| 인터페이스 정의    | 명시적 정의 없음 (Swagger 등 사용) | .proto 파일 기반 명시적 인터페이스 정의   |
| 언어 지원       | 광범위한 언어 지원               | 다양한 언어 지원 (공식 플러그인 필요)      |
| 브라우저 직접 호출  | 가능                       | 불가능 (브라우저가 gRPC 직접 호출 불가)   |
| 디버깅 용이성     | 쉬움 (사람이 읽을 수 있는 JSON)    | 어려움 (바이너리 포맷으로 가독성 낮음)      |
| 버전 관리       | 경로/헤더 등으로 처리             | .proto에서 명시적으로 관리 가능        |
| 개발 생산성      | 빠름 (도구가 많고 친숙함)          | 높음 (코드 자동 생성, 명확한 계약 기반 개발) |
| 마이크로서비스 적합성 | 일반적                      | 고성능, 내부 통신에 적합              |

## 외부 통신 (HTTP)

| 기능     | 파라미터 예시                                        | 설명                |
|--------|------------------------------------------------|-------------------|
| 필드 선택  | `fields=name,age`                              | 응답에서 특정 필드만 선택    |
| 필터링    | `filter[name]=John`, `filter[price][gte]=1000` | 특정 조건에 맞는 데이터 필터링 |
| 정렬     | `sort=-created_at`, `sort=name`                | 특정 필드 기준으로 정렬     |
| 포함 리소스 | `include=author,comments`                      | 관련 리소스 포함         |
| 페이지네이션 | `page=1&limit=10`                              | 페이지네이션 처리         |
| 메타데이터  | `meta=true`                                    | 응답에 메타데이터 포함      |

```http request
GET /products?
    fields=id,name,price&
    filter[price][gte]=1000&
    filter[category]=electronics&
    sort=-price&
    page=1&limit=10&
    include=manufacturer&
    meta=true
```


## signoz 설치 및 실행

### 설치
```bash
git clone -b main https://github.com/SigNoz/signoz.git
```

### 실행 
```bash
cd signoz/deploy/docker && \
docker compose up -d --remove-orphans
```
