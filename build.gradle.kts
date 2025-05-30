plugins {
    kotlin("jvm") version "2.1.20"
    `maven-publish`
}

group = "io.github.footermandev"
version = "0.0.1"

repositories {
    mavenCentral()
}

val osName: String = System.getProperty("os.name")
val targetOs = when {
    osName == "Mac OS X" -> "macos"
    osName.startsWith("Win") -> "windows"
    osName.startsWith("Linux") -> "linux"
    else -> error("Unsupported OS: $osName")
}

val osArch: String = System.getProperty("os.arch")
val targetArch = when (osArch) {
    "x86_64", "amd64" -> "x64"
    "aarch64" -> "arm64"
    else -> error("Unsupported arch: $osArch")
}

val skikoTarget = "${targetOs}-${targetArch}"
val skiko = "0.8.9"
val coroutines = "1.10.2"

dependencies {
    implementation("org.jetbrains.skiko:skiko-awt-runtime-$skikoTarget:$skiko")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutines")
}

kotlin {
    jvmToolchain(22)
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            artifactId = "deuterium"

            pom {
                name.set("Deuterium")
                description.set("Replacement for Swing using Skiko.")
                url.set("https://github.com/FooterManDev/Deuterium")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("footermandev")
                        name.set("FooterManDev")
                        url.set("https://github.com/FooterManDev")
                    }
                }

                scm {
                    url.set("https://github.com/FooterManDev/Deuterium")
                    connection.set("scm:git:ssh://git@github.com:FooterManDev/Deuterium.git")
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/FooterManDev/Deuterium")

            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN_MAVEN")
            }
        }
    }
}
