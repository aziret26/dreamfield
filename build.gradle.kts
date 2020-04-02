import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

apply(plugin = "java")
apply(plugin = "idea")
group = "kg.task"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

buildscript {
	repositories {
		jcenter()
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:2.2.6.RELEASE")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.71")
		classpath("org.jetbrains.kotlin:kotlin-allopen:1.3.71")
		classpath("org.jetbrains.kotlin:kotlin-noarg:1.3.71")
	}
}

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.3.71"

	kotlin("jvm") version "1.3.71"
	kotlin("plugin.spring") version "1.3.71"
	kotlin("plugin.jpa") version "1.3.71"
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.session:spring-session-data-redis")
	implementation ("org.springframework.session:spring-session-core")
	implementation("org.flywaydb:flyway-core")

	implementation("io.springfox:springfox-swagger-ui:2.9.2")
	implementation("io.springfox:springfox-swagger2:2.9.2")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("org.postgresql:postgresql")



	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.springframework.security:spring-security-test")
}

noArg {
	annotations("org.springframework.boot.context.properties.ConfigurationProperties")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
