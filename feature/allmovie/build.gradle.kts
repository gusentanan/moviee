plugins {
    id("moviee.android.feature")
}

android {
    namespace = "com.bagusmerta.feature.allmovie"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature:detail"))
    implementation(project(":utility"))

    implementation(libs.lottie)
    implementation(libs.shimmer)
    implementation(libs.timber)
}