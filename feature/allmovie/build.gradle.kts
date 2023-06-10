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
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.airbnb.android:lottie:4.1.0")
}