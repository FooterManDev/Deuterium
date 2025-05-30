plugins {
    kotlin("jvm") version "2.0.21"
}

group = "io.github.footermandev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.github.com/FooterManDev/Deuterium") }
}

dependencies {
    implementation("io.github.footermandev.deuterium:0.0.1")
}

kotlin {
    jvmToolchain(22)
}