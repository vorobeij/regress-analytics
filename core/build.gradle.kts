plugins {
    kotlin("jvm")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}