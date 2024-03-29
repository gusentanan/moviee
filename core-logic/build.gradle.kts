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
    id("moviee.android.core_logic")
}

android {
    namespace = "com.bagusmerta.core_logic"
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {

        buildConfigField("String", "API_KEY", "\"e87f1caa9ba887b36d2b6613e7759f6f\"")
        buildConfigField("String", "BASE_API", "\"https://api.themoviedb.org/3/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":utility"))
    implementation(libs.junit)
    implementation(libs.mockito.core)
    implementation(libs.mockito.kotlin)
    implementation(libs.mockito.inline)
    implementation(libs.timber)

    implementation(libs.androidx.paging.ktx)
    implementation("androidx.paging:paging-rxjava2:3.1.1")
}