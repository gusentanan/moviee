// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.ksp) apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.withType<Test> {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events("started", "skipped", "passed", "failed")
        showStandardStreams = true
    }
}

gradle.taskGraph.addTaskExecutionGraphListener { graph ->
    if (graph.hasTask(":lint")) {
        tasks.getByName("lint").enabled = false
    }
}
