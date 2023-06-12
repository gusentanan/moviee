plugins {
    id("moviee.android.library")
}

android {
    namespace = "com.bagusmerta.utility"
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    defaultConfig {
        buildConfigField("String", "POSTER_URL", "\"https://image.tmdb.org/t/p/w500\"")
        buildConfigField("String", "POSTER_URL_HQ", "\"https://image.tmdb.org/t/p/w780/\"")
    }
}