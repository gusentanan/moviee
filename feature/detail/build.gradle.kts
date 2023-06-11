plugins {
    id("moviee.android.feature")
}

android {
    namespace = "com.bagusmerta.feature.detail"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":utility"))

    implementation(libs.shimmer)
    implementation(libs.timber)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.compiler)

    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.0.1")
}