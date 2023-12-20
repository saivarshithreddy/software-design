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
 
sourceSets {
  main {
    java.srcDirs("src")
  }
  test {
    java.srcDirs("test", "src")
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
        exclude("evaluator/ui")
      }
    }))
  }
}

tasks.register<JavaExec>("run") {
  group = "Application"
  description = "Run the main class"
  mainClass = "evaluator.ui.ApplicationEvaluatorUI"
  classpath = sourceSets.main.get().runtimeClasspath
  standardInput = System.`in`
}

defaultTasks("clean", "test", "jacocoTestReport", "pmdMain")
