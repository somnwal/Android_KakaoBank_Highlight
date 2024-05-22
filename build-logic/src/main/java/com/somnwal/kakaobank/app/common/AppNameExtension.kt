package com.somnwal.kakaobank.app.common

import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "com.somnwal.kakaobank.app.$name"
    }
}