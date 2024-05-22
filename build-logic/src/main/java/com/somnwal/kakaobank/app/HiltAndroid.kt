package com.somnwal.kakaobank.app

import com.somnwal.kakaobank.app.common.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.android")
        apply("org.jetbrains.kotlin.kapt")
    }

    val libs = extensions.libs

    dependencies {
        "implementation"(libs.findLibrary("hilt.android").get())
        "kapt"(libs.findLibrary("hilt.android.compiler").get())
    }
}

internal class HiltAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        // 해당 타겟 프로젝트들에게 Hilt를 적용한다.
        with(target) {
            configureHiltAndroid()
        }
    }
}