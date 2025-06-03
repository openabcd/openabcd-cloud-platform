의도와 의존성 처리 방식에서 중요한 차이점이 있습니다.

## ✅ 공통점

- 둘 다 Gradle에서 Java 프로젝트를 구성하기 위한 플러그인입니다.
- compileJava, test, jar 등의 Task를 제공합니다.
- 둘 다 dependencies 블록에서 implementation, api, compileOnly, runtimeOnly 등 DSL을 사용할 수 있게 해줍니다.

## ✅ 차이점 요약

| 항목        | java                           | java-library                                |
|-----------|--------------------------------|---------------------------------------------|
| 목적        | 일반적인 Java 애플리케이션               | 라이브러리 개발을 위한 모듈                             |
| 의존성 노출 방식 | implementation/api 차이 없음       | api와 implementation을 구분하여 모듈 경계가 명확         |
| 접근 범위     | 모든 의존성이 compile classpath에 포함됨 | api: 외부에 노출됨<br/> implementation: 내부에서만 사용됨 |
| 예제 용도     | CLI 앱, Spring Boot 앱 등         | 공통 모듈, 유틸 라이브러리 등                           |

## ✅ 핵심: api vs implementation

- `java-library` 플러그인을 적용하면 api와 implementation의 차이를 Gradle이 빌드 시 classpath 수준에서 분리해 줍니다.
- 예: :common 모듈이 guava를 implementation으로 선언하면, 이를 의존하는 :api-gateway 모듈에서는 guava를 사용할 수 없습니다. -> **캡슐화** 보장됨.

## ✅ 언제 어떤 걸 써야 할까?

| 상황                               | 선택             |
|----------------------------------|----------------|
| 라이브러리 모듈 (:common, :core, :util) | ✅ java-library |
| 실행되는 앱 (Spring Boot 등)           | ✅ java         |

## ✅ 예시

```kotlin
// java-library 사용 시
dependencies {
    api("org.apache.commons:commons-lang3:3.12.0")         // 외부에 노출됨
    implementation("com.google.guava:guava:32.1.1-jre")   // 내부용
}
```

## ✅ 결론

> [!TIP]  
> java-library는 의존성 캡슐화를 통해 재사용 가능한 모듈을 만들 때 유리합니다.
> 특히 :common 같은 모듈엔 java-library 플러그인을 사용하는 게 베스트 프랙티스입니다.

