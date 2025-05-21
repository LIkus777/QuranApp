pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "QuranApp"
include(":app")
include(":domain")
include(":di")
include(":core")
include(":presentation")
include(":data")
include(":navigation")
include(":features")
include(":app", ":unityLibrary")
project(":unityLibrary").projectDir = file("C:\\Users\\zm393\\Masjid\\android\\unityLibrary")
