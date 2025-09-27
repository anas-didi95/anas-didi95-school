import java.util.Properties

plugins {
  id("io.micronaut.application") version "4.5.3"
  id("com.gradleup.shadow") version "8.3.6"
  id("io.micronaut.aot") version "4.5.3"
  id("com.diffplug.spotless") version "7.0.2"
}

version = "v0.1.0"

group = "com.anasdidi.school"

repositories { mavenCentral() }

dependencies {
  annotationProcessor("org.projectlombok:lombok")
  annotationProcessor("io.micronaut.data:micronaut-data-processor")
  annotationProcessor("io.micronaut:micronaut-http-validation")
  annotationProcessor("io.micronaut.openapi:micronaut-openapi")
  annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
  annotationProcessor("io.micronaut.validation:micronaut-validation-processor")
  implementation("io.micronaut:micronaut-jackson-databind")
  implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
  implementation("io.micronaut.liquibase:micronaut-liquibase")
  implementation("io.micronaut.serde:micronaut-serde-jackson")
  implementation("io.micronaut.sql:micronaut-jdbc-hikari")
  implementation("io.micronaut.validation:micronaut-validation")
  implementation("jakarta.validation:jakarta.validation-api")
  implementation("org.slf4j:jul-to-slf4j")
  implementation("org.slf4j:jcl-over-slf4j")
  implementation("ch.qos.logback:logback-classic")
  implementation("org.springframework.security:spring-security-crypto:6.3.4")
  implementation("io.vertx:vertx-core:5.0.4")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  compileOnly("io.micronaut:micronaut-http-client")
  compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")
  compileOnly("org.projectlombok:lombok")
  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.yaml:snakeyaml")
  testImplementation("io.micronaut:micronaut-http-client")
}

application { mainClass = "com.anasdidi.school.Application" }

java {
  sourceCompatibility = JavaVersion.toVersion("21")
  targetCompatibility = JavaVersion.toVersion("21")
}

graalvmNative.toolchainDetection = false

micronaut {
  runtime("netty")
  testRuntime("junit5")
  processing {
    incremental(true)
    annotations("com.anasdidi.school.*")
  }
  aot {
    // Please review carefully the optimizations enabled below
    // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
    optimizeServiceLoading = false
    convertYamlToJava = false
    precomputeOperations = true
    cacheEnvironment = true
    optimizeClassLoading = true
    deduceEnvironment = true
    optimizeNetty = true
    replaceLogbackXml = true
  }
}

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
  jdkVersion = "21"
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
  format("misc") {
    target(".gitattributes", ".gitignore")
    trimTrailingWhitespace()
    leadingTabsToSpaces(2)
    endWithNewline()
  }
  java {
    importOrder()
    removeUnusedImports()
    cleanthat()
    googleJavaFormat()
    formatAnnotations()
    licenseHeader("/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */")
  }
  // groovyGradle {
  //  target("*.gradle") // default target of groovyGradle
  //  greclipse()
  // }
  kotlinGradle {
    target("*.gradle.kts") // default target for kotlinGradle
    ktfmt()
    // ktlint() // or ktfmt() or prettier()
  }
}

// val commitId: String by lazy {
//  val stdout = ByteArrayOutputStream()
//  rootProject.exec {
//    commandLine("git", "rev-parse", "--short", "HEAD")
//    standardOutput = stdout
//  }
//  stdout.toString().trim()
// }

tasks.register("createProperties") {
  dependsOn(tasks.processResources)

  doLast {
    val versionFile = file("$buildDir/resources/main/version.properties")
    versionFile.parentFile.mkdirs() // Ensure that the parent directories exist

    versionFile.printWriter().use { writer ->
      val properties = Properties()
      properties["projectName"] = project.name.toString()
      properties["version"] = project.version.toString()
      // properties["commitId"] = commitId
      properties.store(writer, null)
    }
  }
}

tasks.classes { dependsOn(tasks.named("createProperties")) }
