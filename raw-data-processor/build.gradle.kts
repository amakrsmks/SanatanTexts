plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    application
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

application {
    mainClass.set("com.amaksmks.rawdataprocessor.RawDataProcessorKt")
}

dependencies {
    implementation(libs.kotlinx.serialization)
    implementation("org.xerial:sqlite-jdbc:3.45.1.0")
}
