plugins {
    kotlin("multiplatform")
    id("com.squareup.sqldelight")
}

// TODO work around https://youtrack.jetbrains.com/issue/KT-27170
configurations.create("compileClasspath")

sqldelight {
    packageName = "com.sample.todo.data.sqldelight"
    className = "TodoSqlDelightDatabase"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("com.squareup.sqldelight:runtime")
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
            }
        }
        commonTest {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
            }
        }
        jvm("android").compilations["main"].defaultSourceSet {
            dependencies {
                api("com.squareup.sqldelight:runtime")
            }
        }
        jvm("android").compilations["test"].defaultSourceSet {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

    }
}
