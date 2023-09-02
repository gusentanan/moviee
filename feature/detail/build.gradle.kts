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
    id("moviee.android.feature")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.bagusmerta.feature.detail"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core-logic"))
    implementation(project(":utility"))
    implementation(project(":common-ui"))

    implementation(libs.shimmer)
    implementation(libs.timber)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.compiler)

    // YT player dep
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.0.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.4.0")
}