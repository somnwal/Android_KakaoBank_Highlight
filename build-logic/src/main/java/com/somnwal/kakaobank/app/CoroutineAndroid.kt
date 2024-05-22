package com.somnwal.kakaobank.app

import com.somnwal.kakaobank.app.common.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCoroutineAndroid() {
    val libs = extensions.libs

    dependencies {
        "implementation"(libs.findLibrary("coroutines.android").get())
    }
}

internal fun Project.configureCoroutineKotlin() {
    val libs = extensions.libs

    dependencies {
        "implementation"(libs.findLibrary("coroutines.core").get())
    }
}