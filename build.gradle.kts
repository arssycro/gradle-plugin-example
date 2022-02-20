plugins {
    kotlin("jvm") version "1.5.10"
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
}

gradlePlugin {
    plugins {
        create("plugin") {
            id = "com.arssycro.gradle"
            implementationClass = "com.arssycro.gradle.UberPlugin"
        }
    }
}