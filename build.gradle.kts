ext["kotlintestVersion"] = "3.1.10"
ext["slf4jVersion"] = "1.7.25"

plugins {
    kotlin("jvm") version "1.3.61"
}

group = "com.30secondsofkotlin"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-RC2")
    testCompile("io.kotlintest:kotlintest-runner-junit5:${project.ext["kotlintestVersion"]}")
    testRuntime("org.slf4j:slf4j-nop:${project.ext["slf4jVersion"]}")
}

kotlin {
    sourceSets {
        getByName("main").kotlin.srcDirs("src/main")
        getByName("test").kotlin.srcDirs("src/test")
    }
}
