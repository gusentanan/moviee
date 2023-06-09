import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import com.bagusmerta.moviee.configureKotlinAndroid

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                // koin DONE
                // kotlin IN LIB
                // kotlincore IN LIB
                // rxJava DONE
                // androidx lifecycle DONE
                // support recycleview DONE

                // timber DONE

                // glide DONE
                // room DONE

                add("implementation", libs.findLibrary("androidx.lifecycle").get())
                add("implementation", libs.findLibrary("recyclerview").get())
                add("implementation", libs.findLibrary("glide").get())
                add("implementation", libs.findLibrary("timber").get())
                add("implementation", libs.findLibrary("shimmer").get())

                add("implementation", libs.findLibrary("koin.core").get())
                add("implementation", libs.findLibrary("koin.android").get())

                add("implementation", libs.findLibrary("rxjava").get())
                add("implementation", libs.findLibrary("rx.android").get())
                add("implementation", libs.findLibrary("rx.stream").get())

                add("implementation", libs.findLibrary("androidx.room.runtime").get())
                add("implementation", libs.findLibrary("androidx.room.ktx").get())
                add("implementation", libs.findLibrary("androidx.room.compiler").get())
            }
        }
    }
}