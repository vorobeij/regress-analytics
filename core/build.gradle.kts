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
}