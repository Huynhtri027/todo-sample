import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//import com.android.build.gradle.kts.BaseExtension
//import org.apache.commons.io.output.TeeOutputStream
//import org.jetbrains.kotlin.gradle.dsl.Coroutines
//import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
//import org.jetbrains.kotlin.gradle.internal.CacheImplementation

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.fabric.io/public")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/mockito/maven")
    }
    dependencies {
        // classpath for kapt
        classpath("com.squareup.sqldelight:gradle-plugin:1.0.3")
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.kotlin_gradle_plugin)
        classpath(Libs.navigation_safe_args_gradle_plugin)
        classpath(Libs.google_services)
        classpath(Libs.io_fabric_tools_gradle)
        classpath("org.jetbrains.kotlin:kotlin-allopen:${Versions.org_jetbrains_kotlin}")


    }
}

plugins {
    id("de.fayard.buildSrcVersions") version "0.3.2"
    id("project-report")
    id("com.diffplug.gradle.spotless") version "3.17.0"

    `build-scan`
}


allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://dl.bintray.com/mockito/maven")
        jcenter()
    }
}



buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
    publishAlways()
}



subprojects {
    apply(plugin = "com.diffplug.gradle.spotless")
    // TODO how to config kapt
    spotless {
        kotlin {
            target("**/*.kt")
            ktlint("0.29.0")
        }
        xml {

        }
    }
    tasks.withType<KotlinCompile>().configureEach {
        println("Configuring $name in project ${project.name}...")
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-XXLanguage:+InlineClasses",
                "-Xallow-result-return-type"
            )
            incremental = true
            jvmTarget = "1.6"
            languageVersion = "1.3"
            apiVersion = "1.3"
        }
    }
}


gradle.projectsEvaluated {
    todoReport()
}
