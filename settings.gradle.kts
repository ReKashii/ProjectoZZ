pluginManagement {
    repositories {
        // Asegura que Google sea el primero para encontrar los plugins de Android y KSP
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

rootProject.name = "Huerto Hogar"
include(":app")