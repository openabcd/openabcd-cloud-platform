# allprojcets and subprojects의 차이

## ✅ 차이 요약

| 항목             | allprojects {}     | subprojects {}   |
|----------------|--------------------|------------------|
| 적용 대상          | 루트 프로젝트 포함 모든 프로젝트 | 루트 제외, 하위 프로젝트만  |
| 루트 프로젝트에도 적용됨? | ✅ 예                | ❌ 아니오            |
| 언제 사용?         | 전 프로젝트에 공통 설정 필요 시 | 하위 모듈만 설정하고 싶을 때 |


## ✅ 예시로 설명

```kotlin
allprojects {
    repositories {
        mavenCentral()
    }
}
```
- 루트 프로젝트에도 mavenCentral() 저장소를 적용합니다.
- 루트에 코드가 없어도 repositories 블록이 적용됩니다.

```kotlin
subprojects {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
}
```

- 루트 프로젝트는 제외하고, 하위 모듈들에만 Java 21 툴체인을 적용합니다.
