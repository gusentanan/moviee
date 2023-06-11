import com.android.build.api.dsl.ApplicationExtension
import com.bagusmerta.moviee.BuildAndroidConfig
import com.bagusmerta.moviee.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

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
                defaultConfig.applicationId = BuildAndroidConfig.application_id
                defaultConfig.targetSdk = BuildAndroidConfig.target_sdk
                defaultConfig.versionCode = BuildAndroidConfig.version_code
                defaultConfig.versionName = BuildAndroidConfig.version_name
            }
        }
    }

}