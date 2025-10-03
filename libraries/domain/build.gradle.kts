plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    //id("com.google.devtools.ksp") version libs.versions.ksp.get()
    //alias(libs.plugins.apollo)
    //kotlin("kapt")
}

android {
    namespace = "com.nafepay.domain"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":libraries:core"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //implementation(libs.apollo.runtime)
    //implementation(libs.apollo.normalized.cache) // optional if you want caching
    api(libs.okhttp)
    api(libs.okhttp.logging)

    api(libs.ktor.core)
    api(libs.ktor.cio)
    api(libs.ktor.logging)
    api(libs.ktor.serialization)
    api(libs.ktor.auth)
    api(libs.ktor.content.negotiation)
    api(libs.ktor.client.okhttp)

    implementation(libs.gson)

    implementation(libs.okhttp.logging)

    implementation(libs.kotlinx.coroutines)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    //ksp(libs.room.compiler)
}
//apollo {
//    service("user") {
//        sourceFolder.set("user")
//        packageName.set("com.nafepay.domain.user")
//    }
//}