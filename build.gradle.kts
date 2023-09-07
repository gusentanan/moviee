/*
 * Designed and developed by 2023 gusentanan (Bagus Merta)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Top-level build file where you can add configuration options common to all sub-projects/modules.
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
    id("com.android.library") version "8.0.2" apply false
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
