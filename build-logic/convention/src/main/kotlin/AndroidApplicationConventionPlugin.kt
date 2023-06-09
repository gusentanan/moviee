import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import com.bagusmerta.moviee.configureKotlinAndroid

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
                apply("kotlin-kapt")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
            }

//            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
//
//            dependencies {
//                add("implementation", project(":core"))
//                add("implementation", project(":utility"))
//
//                add("implementation", libs.findLibrary("androidx.core.ktx"))
//                add("implementation", libs.findLibrary("androidx.activity.ktx"))
//                add("implementation", libs.findLibrary("androidx.appcompat"))
//                add("implementation", libs.findLibrary("androidx.lifecycle"))
//                add("implementation", libs.findLibrary("recyclerview"))
//                add("implementation", libs.findLibrary("glide"))
//                add("implementation", libs.findLibrary("timber"))
//
//                add("implementation", libs.findLibrary("koin.core"))
//                add("implementation", libs.findLibrary("koin.android"))
//
//                add("implementation", libs.findLibrary("rxjava"))
//                add("implementation", libs.findLibrary("rx.android"))
//                add("implementation", libs.findLibrary("rx.stream"))
//            }
        }
    }

}