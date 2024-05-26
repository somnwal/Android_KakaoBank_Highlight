import com.somnwal.kakaobank.app.common.setNamespace

plugins {
    id("somnwal.kakaobank.android.feature")
}

android {
    setNamespace("feature.main")
}

dependencies {
    implementation(projects.feature.search)
    implementation(projects.feature.favorite)

    implementation(projects.core.dataApi)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.navigation)

    implementation(libs.android.material)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.immutable)
}