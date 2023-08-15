val kotlinVersion by extra("1.8.0")

buildscript {
    repositories {
        google()
    }
    dependencies {
        val navVersion = "2.7.0"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")

    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.library") version "8.1.0" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
    kotlin("plugin.serialization") version "1.9.0"
}