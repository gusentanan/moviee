plugins {
    id("moviee.android.core")
}

android {
    namespace = "com.bagusmerta.core"
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {

        buildConfigField("String", "API_KEY", "\"e87f1caa9ba887b36d2b6613e7759f6f\"")
        buildConfigField("String", "BASE_API", "\"https://api.themoviedb.org/3/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":utility"))
    implementation(libs.junit)
    implementation(libs.mockito.core)
    implementation(libs.mockito.kotlin)
    implementation(libs.mockito.inline)
    implementation(libs.timber)
}