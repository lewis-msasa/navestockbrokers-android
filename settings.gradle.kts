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

rootProject.name = "stock-broker"
include(":app")
include(":libraries:ui:authentication")
include(":libraries:ui:common_ui")
include(":libraries:ui:portfolio")
include(":libraries:ui:stocks")
include(":libraries:core")
include(":libraries:domain")
include(":libraries:navigation")
include(":libraries:ui:main")
