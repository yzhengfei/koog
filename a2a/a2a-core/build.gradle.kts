import ai.koog.gradle.publish.maven.Publishing.publishToMaven

val isBeta by extra(true)

plugins {
    id("ai.kotlin.multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

val isJsEnabled = (findProperty("koog.target.js") as? String)?.toBoolean() ?: true

kotlin {
    jvm()

    if (isJsEnabled) {
        js(IR) {
            browser()
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.coroutines.core)
                implementation(project(":agents:agents-utils"))
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }

        if (isJsEnabled) {
            jsTest {
                dependencies {
                    implementation(kotlin("test-js"))
                }
            }
        }
    }

    explicitApi()
}

publishToMaven()
