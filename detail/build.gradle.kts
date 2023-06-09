plugins {
    id("moviee.android.feature")
}

android {
    namespace = "com.bagusmerta.detail"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":utility"))
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.0.1")

}