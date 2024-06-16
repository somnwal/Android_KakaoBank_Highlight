plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "somnwal.kakaobank.android.hilt"
            // 위의 id로 plugin에 명시한 모듈들은 모두 HiltAndroidPlugin 클래스를 따른다.
            implementationClass = "com.somnwal.kakaobank.app.HiltAndroidPlugin"
        }
    }
}