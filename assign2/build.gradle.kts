import org.gradle.internal.execution.ExecutionEngine.Execution
import org.gradle.internal.impldep.org.eclipse.jgit.lib.ObjectChecker.type
import java.io.ByteArrayInputStream

plugins {
  java
  jacoco
  pmd
}

repositories {
	mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("org.junit.platform:junit-platform-console:1.9.2")
    testImplementation("org.mockito:mockito-core:2.28.2")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")

}


tasks {
  val flags = listOf("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")

  getByName<JavaCompile>("compileJava") {
    options.compilerArgs = flags
  }

  getByName<JavaCompile>("compileTestJava") {
    options.compilerArgs = flags
  }
}

tasks.register<JavaExec>("run") {
    group = "Application"
    description = "Run the main class"
    mainClass = "processor.SpellCheckerUI"
    classpath = sourceSets.main.get().runtimeClasspath
    standardInput = ByteArrayInputStream("sample.txt".toByteArray())
}

sourceSets {
  main {
    java.srcDirs("src")
  }
  test {
    java.srcDirs("test")
  }
}

val test by tasks.getting(Test::class) {
	useJUnitPlatform {}
}

pmd {
    ruleSets = listOf()
    ruleSetFiles = files("../conf/pmd/ruleset.xml")
    toolVersion = "6.54.0"
}

tasks.withType<JacocoReport> {
  afterEvaluate {
    classDirectories.setFrom(files(classDirectories.files.map {
      fileTree(it).apply {
        exclude("*/preview/*")
      }
    }))
  }
}

defaultTasks("clean", "test", "jacocoTestReport", "pmdMain")
