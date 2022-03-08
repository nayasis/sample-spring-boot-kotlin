import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.serialization") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	kotlin("plugin.allopen") version "1.6.10"
	kotlin("plugin.noarg") version "1.6.10"
	kotlin("plugin.jpa") version "1.6.10"
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

noArg {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
	annotation("com.github.nayasis.kotlin.basica.annotation.NoArg")
	invokeInitializers = true
}

group = "com.github.nayasis.sample.springboot"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations.all {
	resolutionStrategy.cacheChangingModulesFor(0, "seconds")
	resolutionStrategy.cacheDynamicVersionsFor(5, "minutes")
}

repositories {
	mavenLocal()
	mavenCentral()
	jcenter()
	maven { url = uri("https://jitpack.io") }
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2021.0.0")
	}
}

dependencies {

	// common
	implementation("com.github.nayasis:basica-kt:0.1.13")
//	implementation("com.github.nayasis:extension-spring-kt:0.1.4")
	implementation("com.github.nayasis:extension-spring-kt:develop-SNAPSHOT") { isChanging = true }
	implementation("commons-codec:commons-codec:1.13")
	implementation("au.com.console:kassava:2.1.0")
	implementation("io.github.microutils:kotlin-logging:2.0.10")

	// spring
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(group="org.springframework.boot",module="spring-boot-starter-tomcat")
	}
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-undertow")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-configuration-processor")

	// spring extention
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.+")
	implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:1.3.0.RELEASE")

	// redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.apache.commons:commons-pool2")
	implementation("it.ozimov:embedded-redis:0.7.2")

	// distributed lock
	implementation("org.springframework.integration:spring-integration-jdbc:5.5.3")
	implementation("org.springframework.integration:spring-integration-redis:5.5.3")
	implementation("org.springframework.integration:spring-integration-core:5.5.3")

	// db
//	implementation("com.h2database:h2")
//  implementation("mysql:mysql-connector-java:8.0.28")
	implementation("org.mariadb.jdbc:mariadb-java-client")

	// kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group="org.junit.vintage",module="junit-vintage-engine")
		exclude(group="com.vaadin.external.google",module="android-json")
	}

}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf(
			"-Xjsr305=strict"
		)
		jvmTarget = "1.8"
	}
}