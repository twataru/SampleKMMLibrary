import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

val librayVer = "0.0.2"


plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.8.10"
}

kotlin {
    cocoapods {
        // Required properties
        // Specify the required Pod version here. Otherwise, the Gradle project version is used.
        version = librayVer
        summary = "Some description for a Kotlin/Native module"
        homepage = "https://github.com/wataru-taniuchi/sample-kmm-library"

        // Optional properties
        // Configure the Pod name here instead of changing the Gradle project name
        name = "KMMSample"

        framework {
            // Required properties
            // Framework name configuration. Use this property instead of deprecated 'frameworkName'
            baseName = "SampleKMMLibrary"
            // Bitcode embedding
            embedBitcode("bitcode")
        }

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }

    multiplatformSwiftPackage {
        targetPlatforms {
            iOS { v("13") }
        }
        swiftToolsVersion("5.3")
        outputDirectory(File(buildDir, "swiftPM"))
        zipFileName("SampleKMMLibrary.xcframework")
        distributionMode { local() }
    }

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            xcf.add(this)
        }
    }

    sourceSets {
        val ktorVersion = "2.2.4"

        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.wataru_taniuchi.sandbox.sample_kmm_library"
    compileSdk = 33
    defaultConfig {
        minSdk = 28
    }
}

publishing {
    repositories {
        maven {
            group = "com.wataru_taniuchi"
            version = librayVer
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/wataru-taniuchi/sample-kmm-library")
            credentials {
                username = project.findProperty("gpr.user") as String?
                password = project.findProperty("gpr.key") as String?
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
        }
    }
}