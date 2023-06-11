import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.bagusmerta.moviee.buildLogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "moviee.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidCore") {
            id = "moviee.android.core"
            implementationClass = "AndroidCoreConventionPlugin"
        }
        register("androidFeature") {
            id = "moviee.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibrary") {
            id = "moviee.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}