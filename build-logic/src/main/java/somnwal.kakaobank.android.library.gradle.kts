import com.somnwal.kakaobank.app.configureCoroutineAndroid
import com.somnwal.kakaobank.app.configureHiltAndroid
import com.somnwal.kakaobank.app.configureKotlinAndroid

// android library 모듈 프로젝트에 적용할 요소들

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureHiltAndroid()
configureCoroutineAndroid()