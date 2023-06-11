import com.android.build.gradle.LibraryExtension
import com.bagusmerta.moviee.BuildAndroidConfig
import com.bagusmerta.moviee.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = BuildAndroidConfig.target_sdk
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", libs.findLibrary("glide").get())
                add("implementation", libs.findLibrary("timber").get())
                add("implementation", libs.findLibrary("rxjava").get())
                add("implementation", libs.findLibrary("rx.android").get())
                add("implementation", libs.findLibrary("rx.stream").get())
            }
        }
    }
}