pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "moviee"
include(":app")
include(":core")
include(":feature:detail")
include(":feature:allmovie")
include(":feature:favoritee")
include(":feature:search")
include(":utility")