@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.somnwal.kakaobank.highlight.app.core.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    implementation(projects.data.model)

    implementation(libs.bundles.androidx.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.hilt.navigation.compose)

    implementation(libs.bundles.coil.compose)
    debugImplementation(libs.bundles.androidx.compose.debug)
}