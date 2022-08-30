plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":core"))
    implementation(KotlinX.cli)
    testImplementation(Testing.junit.jupiter)
    testImplementation(Testing.junit.jupiter.api)
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
