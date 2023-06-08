import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include

plugins {
    id("moviee.android.application")
}

android {
    namespace = "com.bagusmerta.moviee"
    defaultConfig {
        applicationId = "com.bagusmerta.moviee"
        versionCode = 3
        versionName = "0.0.3"
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }

    dynamicFeatures = mutableSetOf(":favoritee")

    configurations {
        implementation {
            exclude(group = "org.jetbrains", module = "annotations")
        }
    }

    lintOptions {
        isAbortOnError = false
    }
}


dependencies {

    implementation(project(":core"))
    implementation(project(":utility"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle)
    implementation(libs.recyclerview)
    implementation(libs.glide)
    implementation(libs.timber)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(libs.rxjava)
    implementation(libs.rx.android)
    implementation(libs.rx.stream)

    implementation("com.airbnb.android:lottie:4.1.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.core:core-splashscreen:1.0.0-alpha02")
    // youtube player dep
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.0.1")
    //stepper dep
    implementation("com.tbuonomo:dotsindicator:4.3")

}