// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.hilt) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}