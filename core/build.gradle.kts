plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(KotlinX.cli)
    implementation(KotlinX.serialization.core)
    implementation(KotlinX.serialization.json)
    testImplementation("io.mockk:mockk-dsl:_")
    testImplementation(Koin.test)
    testImplementation(Testing.junit.jupiter)
    testImplementation(Testing.junit.jupiter.api)
    testImplementation(Testing.mockK)
}