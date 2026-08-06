<a id="readme-top"></a>

<div align="center">

# maven-invoker-spring-boot-starter

**Spring Boot Starter for maven-invoker**

[![Maven Central](https://img.shields.io/maven-central/v/io.github.easy4j/maven-invoker-spring-boot-starter)](https://github.com/easy-4-java/maven-invoker-spring-boot-starter)
[![Java](https://img.shields.io/badge/Java-17-orange)](#3-requirements-and-compatibility)
[![License](https://img.shields.io/badge/license-Apache-2.0-green)](https://www.apache.org/licenses/LICENSE-2.0)

[简体中文](./README.zh-CN.md) | [English](./README.md)

[Positioning](#1-positioning) · [Capabilities](#2-core-capabilities) ·
[Dependency](#5-dependency) · [Quick Start](#6-quick-start) ·
[Configuration](#7-configuration-reference) · [Versions](#9-version-lines-and-compatibility) ·
[Build](#10-build-and-test) · [License](#12-license)

</div>

---

> **Current Version**：`2.0.0.RELEASE`<br>
> **JDK Baseline**：`17`<br>
> **Group ID**：`io.github.easy4j`<br>
> **Artifact ID**：`maven-invoker-spring-boot-starter`<br>
> **License**：Apache License 2.0<br>

## 1. Positioning

**maven-invoker-spring-boot-starter** is a Spring Boot starter that integrates **maven-invoker** for applications using maven-invoker. It provides auto-configuration, property binding, and ready-to-use beans so that applications can consume maven-invoker capabilities with minimal setup.

| Dimension | Description |
|---|---|
| Type | Spring Boot Starter |
| Consumers | Spring Boot applications using maven-invoker |
| Core Capabilities | auto-configuration, property binding, ready-to-use beans for maven-invoker |
| JDK | `17` |
| Coordinates | `io.github.easy4j:maven-invoker-spring-boot-starter:2.0.0.RELEASE` |
| Config Prefix | `maven.invoker` |

## 2. Core Capabilities

| Capability | Status | Description |
|---|:---:|---|
| Auto-configuration | ✅ Stable | Registers maven-invoker beans automatically |
| Property Binding | ✅ Stable | Binds `maven.invoker.*` to `MavenInvokerProperties` |
| `InvocationOutputHandler` bean | ✅ Stable | Auto-registered via MavenInvokerAutoConfiguration |

## 3. Requirements and Compatibility

| Dependency | Minimum | Evidence |
|---|---:|---|
| JDK | `17` | `pom.xml` |
| Spring Boot | `2.6.0` | `pom.xml` parent |
| Maven | `3.6+` | Maven Enforcer |

## 4. Auto-configuration

The starter auto-configures the following beans:

| Bean | Condition | Missing Behavior |
|---|---|---|
| `InvocationOutputHandler` | classpath + property | not created |
| `InvocationOutputHandler` | classpath + property | not created |
| `InvokerLogger` | classpath + property | not created |
| `Invoker` | classpath + property | not created |
| `MavenInvokerTemplate` | classpath + property | not created |

Auto-configuration registration:

- `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` (Spring Boot 2.7+ / 3.x / 4.x)
- `META-INF/spring.factories` (Spring Boot 2.x legacy)

## 5. Dependency

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>maven-invoker-spring-boot-starter</artifactId>
    <version>2.0.0.RELEASE</version>
</dependency>
```

No additional easy4j component dependencies.

## 6. Quick Start

### 6.1 Add dependency

Add the dependency above to your `pom.xml`.

### 6.2 Configure

```yaml
maven.invoker:
  enabled: true
```

### 6.3 Use the bean

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

Then inject the auto-configured bean in your code:

```java
@Autowired
private InvocationOutputHandler outputHandler;
```

## 7. Configuration Reference

### 7.1 Config Prefix

`maven.invoker`

### 7.2 Configuration Items

| Property | Type | Default | Required | Description | Sensitive |
|---|---|---|:---:|---|:---:|
| `maven.invoker.enabled` | boolean | `true` | No | Enable the starter | No |
<!-- additional properties below -->

## 8. Version Lines and Compatibility

| Branch | JDK | Spring Boot | Component Version | Status |
|---|---:|---:|---|:---:|
| `2.3.x` / `2.7.x` | `8+` | 2.3.x / 2.7.x | `1.0.x` | Maintenance |
| `3.0.x` ~ `3.5.x` | `17` | 3.x | `2.0.x` | Maintenance |
| `4.0.x` / `4.1.x` | `17+` | 4.x | `3.0.x` | Active |

## 9. Build and Test

```bash
mvn clean verify
mvn -pl maven-invoker-spring-boot-starter -am test
```

## 10. Troubleshooting

| Symptom | Diagnosis | Resolution |
|---|---|---|
| Bean not created | Check auto-configuration report | Verify `maven.invoker.enabled=true` and classpath |
| `ClassNotFoundException` | Missing dependency | Add the required module |
| Version conflict | `mvn dependency:tree` | Use BOM for version alignment |

## 11. Contribution

1. Fork the repository.
2. Create a feature branch.
3. Run `mvn clean verify` before submitting.
4. Submit a pull request.

## 12. License

This project is licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).

---

<div align="center">

[Back to top](#readme-top) · [Issues](https://github.com/easy-4-java/maven-invoker-spring-boot-starter/issues) · [Repository](https://github.com/easy-4-java/maven-invoker-spring-boot-starter)

</div>
