plugins {
    id("moviee.android.application")
}

android {
    namespace = "com.bagusmerta.moviee"

    defaultConfig {
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    configurations {
        implementation {
            exclude(group = "org.jetbrains", module = "annotations")
        }
    }
}


dependencies {

    implementation(project(":core"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:search"))
    implementation(project(":feature:allmovie"))
    implementation(project(":feature:favoritee"))
    implementation(project(":utility"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle)
    implementation(libs.recyclerview)
    implementation(libs.glide)
    implementation(libs.timber)
    implementation(libs.shimmer)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.rxjava)
    implementation(libs.rx.android)
    implementation(libs.rx.stream)
    implementation(libs.lottie)
    implementation(libs.material)
    implementation(libs.splash.screen)

    //stepper dep
    implementation("com.tbuonomo:dotsindicator:4.3")

}