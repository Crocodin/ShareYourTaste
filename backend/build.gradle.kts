plugins {
    java
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ro.iss"
version = "0.0.1-SNAPSHOT"
description = "backend"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Persistence (JPA + Hibernate)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Validation (Jakarta)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // PostgreSQL Driver
    runtimeOnly("org.postgresql:postgresql")

    // Swagger / OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.3")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    // Caching (EhCache)
    implementation("org.ehcache:ehcache:3.10.8") {
        artifact {
            classifier = "jakarta"
        }
    }
    implementation("javax.cache:cache-api:1.1.1")
    implementation("org.springframework.boot:spring-boot-starter-cache")


    // DevTools (hot reload)
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Testing (Spring Boot default)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
