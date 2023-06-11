plugins {
    id("moviee.android.feature")
}

android {
    namespace = "com.bagusmerta.feature.search"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:favoritee"))
    implementation(project(":utility"))

    implementation(libs.timber)
    implementation(libs.lottie)
}