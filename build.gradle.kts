plugins {
    alias(libs.plugins.android.application) apply false
    // Plugin untuk Room Database
    id("com.google.devtools.ksp") version "2.3.12" apply false
}