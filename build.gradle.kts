plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "st.contraptioni.aoc2024"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.9.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "st.contraptioni.aoc2024.MainKt"
}
