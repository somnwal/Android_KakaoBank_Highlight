import com.somnwal.kakaobank.app.common.setNamespace

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("somnwal.kakaobank.android.feature")
}

android {
    setNamespace("feature.favorite")
}

dependencies {
    implementation(libs.kotlinx.immutable)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)
}