plugins {
   id("moviee.android.feature")
}

android {
    namespace = "com.bagusmerta.favoritee"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":detail"))
    implementation(project(":utility"))
    implementation("com.airbnb.android:lottie:4.1.0")
    implementation("com.google.android.material:material:1.9.0")
}