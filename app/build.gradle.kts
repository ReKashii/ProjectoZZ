plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.huertohogar"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.huertohogar"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    // CONFIGURACIÓN DE FIRMA (Esto cumple el ítem IL3.3.1)
    signingConfigs {
        create("release") {
            // Cuando generes tu llave .jks, asegúrate de que coincidan estos nombres
            storeFile = file("keystore_huerto.jks")
            storePassword = "password123"
            keyAlias = "key0"
            keyPassword = "password123"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            // Evita error si no has creado la llave aún
            if (file("keystore_huerto.jks").exists()) {
                signingConfig = signingConfigs.getByName("release")
            }
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // UI
    implementation("androidx.compose.material:material:1.6.7")
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended-android:1.6.7")
    implementation("io.coil-kt:coil-compose:2.6.0")

    // DATASTORE & ROOM
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    // RETROFIT (NECESARIO PARA LA API DEL PROFESOR)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // TESTING (NECESARIO PARA LA RÚBRICA)
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.7")
}