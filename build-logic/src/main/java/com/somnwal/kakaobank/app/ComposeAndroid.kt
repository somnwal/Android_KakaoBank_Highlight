package com.somnwal.kakaobank.app

import com.somnwal.kakaobank.app.common.androidExtension
import com.somnwal.kakaobank.app.common.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeAndroid() {
    val libs = extensions.libs

    androidExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("composeCompiler").get().toString()
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()

            add("implementation", platform(bom))

//            add("implementation", libs.findLibrary("androidx-activity-compose").get())
//            add("implementation", libs.findLibrary("androidx-compose-lifecycle-runtime").get())
//            add("implementation", libs.findLibrary("androidx-compose-runtime").get())


            add("implementation", libs.findLibrary("androidx.compose.material3").get())
            add("implementation", libs.findLibrary("androidx.compose.ui").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.graphics").get())
            add("implementation", libs.findLibrary("androidx.compose.material.iconsExtended").get())

            add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            add("implementation", libs.findLibrary("androidx.compose.navigation").get())
            add("implementation", libs.findLibrary("androidx.compose.paging").get())
        }
    }
}