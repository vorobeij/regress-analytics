plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}


dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":core"))
    api("org.jetbrains.exposed:exposed-core:_")
    api(KotlinX.serialization.core)
    implementation("org.jetbrains.exposed:exposed-java-time:_")
    implementation("org.nield:kotlin-statistics:_")
    implementation(Koin.core)
    implementation(KotlinX.cli)
    implementation(KotlinX.serialization.json)
    runtimeOnly("com.h2database:h2:_")
    runtimeOnly("org.jetbrains.exposed:exposed-dao:_")
    runtimeOnly("org.jetbrains.exposed:exposed-jdbc:_")
    runtimeOnly("org.xerial:sqlite-jdbc:_")
    testImplementation("io.mockk:mockk-dsl:_")
    testImplementation(Koin.test)
    testImplementation(Testing.junit.jupiter)
    testImplementation(Testing.junit.jupiter.api)
    testImplementation(Testing.mockK)
}

// https://github.com/autonomousapps/dependency-analysis-android-gradle-plugin/wiki/Customizing-plugin-behavior
dependencyAnalysis {
    issues {
        ignoreKtx(true)
        onAny {
            severity("fail")
            exclude(
                ":core",
                "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
            )
        }
    }
}

apply(from = "$rootDir/jacoco.gradle")
