import ai.koog.gradle.publish.maven.Publishing.publishToMaven

val isBeta by extra(true)

plugins {
    id("ai.kotlin.multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

val isAndroidEnabled = (findProperty("koog.target.android") as? String)?.toBoolean() ?: true
val isJsEnabled = (findProperty("koog.target.js") as? String)?.toBoolean() ?: true
val isIosEnabled = (findProperty("koog.target.ios") as? String)?.toBoolean() ?: true

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":koog-agents"))
                api(project(":koog-agents-additions"))
                api(project(":utils"))
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.serialization.json)
                api(libs.ktor.server.core)
                implementation(project(":http-client:http-client-ktor"))
            }
        }

        jvmMain {
            dependencies {
                api(project(":agents:agents-mcp"))
            }
        }

        commonTest {
            dependencies {
                implementation(project(":test-utils"))
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.server.test.host)
            }
        }

        if (isAndroidEnabled) {
            androidUnitTest {
                dependencies {
                    implementation(libs.ktor.client.cio)
                }
            }
        }

        if (isJsEnabled) {
            jsTest {
                dependencies {
                    implementation(kotlin("test-js"))
                    implementation(libs.ktor.client.js)
                }
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.server.config.yaml)
            }
        }

        if (isIosEnabled) {
            appleTest {
                dependencies {
                    implementation(libs.ktor.client.darwin)
                }
            }
        }
    }

    explicitApi()
}

publishToMaven()
