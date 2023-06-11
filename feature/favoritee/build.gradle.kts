plugins {
   id("moviee.android.feature")
}

android {
    namespace = "com.bagusmerta.feature.favoritee"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature:detail"))
    implementation(project(":utility"))

    implementation(libs.lottie)
    implementation(libs.timber)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.compiler)
}