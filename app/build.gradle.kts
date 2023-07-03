/*
 * Designed and developed by 2023 gusentanan (Bagus Merta)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    id("moviee.android.application")
}

android {
    namespace = "com.bagusmerta.moviee"

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isMinifyEnabled = false
            isShrinkResources = false
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

    // sdp and ssp dep
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")


    //stepper dep
    implementation("com.tbuonomo:dotsindicator:4.3")

}