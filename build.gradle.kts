buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
        classpath("com.pinterest:ktlint:_")
        classpath("com.autonomousapps:dependency-analysis-gradle-plugin:_")
        classpath("org.cqfn.diktat:diktat-gradle-plugin:_")
    }
}

apply(plugin = "com.autonomousapps.dependency-analysis")

val javaVersion = JavaVersion.VERSION_14

allprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_14.toString()
            javaParameters = true
        }
    }
    val implementation by configurations

    dependencies {
        implementation(Kotlin.stdlib.jdk8)
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    apply(plugin = "org.cqfn.diktat.diktat-gradle-plugin")
    configure<org.cqfn.diktat.plugin.gradle.DiktatExtension> {
        diktatConfigFile = rootProject.file("diktat-analysis.yml")
        inputs {
            exclude("src/resources/**/*.kt")
            include("src/**/*.kt")
        }
    }
}

apply(from = "./ci/testrules/kotlin-tests-rule.gradle")

tasks.register<GradleBuild>("checkAndPublish") {
    tasks = listOf(
        "clean",
        "refreshVersionsMigrate",
        "refreshVersions",
        "buildHealth",
        "kotlinTestRule",
        "diktatFix",
        // "jacocoTestCoverageVerification",
        "build",
        "publishToMavenLocal"
    )
}
