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
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "search"
include(":app")

include(
    ":core:data",
    ":core:data-api",
    ":core:datastore",
    ":core:datastore-proto",
    ":core:designsystem",
    ":core:model",
    ":core:domain"
)

include(
    ":feature:main",
    ":feature:search",
    ":feature:favorite",
)
