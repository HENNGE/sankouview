plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    `maven-publish`
    signing
}

android {
    namespace = "com.hennge.oss.sankou.sankouview"
    compileSdk = 35

    defaultConfig {
        minSdk = 30
        lint.targetSdk = 35

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val sharedTestResource = "src/shared/resources"
        sourceSets.getByName("androidTest") {
            resources.srcDir(sharedTestResource)
        }

        sourceSets.getByName("test") {
            assets.srcDir(files("$projectDir/schemas".toString()))
            resources.srcDir(sharedTestResource)
        }

        sourceSets.getByName("main") {
            resources.srcDir(sharedTestResource)
        }
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.material)
    implementation(libs.material3)
    implementation(libs.ui)
    implementation(libs.ui.tooling)
    implementation(libs.androidx.foundation)
    // Serialization
    implementation(libs.gson)
    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            //setGroupId groupId
            groupId = "com.hennge.sankou"
            //setArtifactId artifactId
            artifactId = "sankouview"
            version = "0.1.2"
            //from components.java

            pom {
                name = "SankouView"
                description = "A simple library for displaying open source license references. Based on Licensee."
                url = "https://github.com/HENNGE/sankouview"

                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }

                developers {
                    developer {
                        id = "charles-hennge"
                        name = "Charles Bond"
                        email = "charles.bond@hennge.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/HENNGE/sankouview.git"
                    developerConnection = "scm:git:ssh://github.com/HENNGE/sankouview.git"
                    url = "https://github.com/HENNGE/sankouview/"
                }
            }

            afterEvaluate {
                from(components["release"])
            }
        }
    }

    repositories {
        mavenLocal()
        maven {
            //url 'https://jitpack.io'
        }
    }
}

signing {
    sign(publishing.publications["release"])
}

//afterEvaluate {
//    publishing {
//        publications {
//            register<MavenPublication>("release") {
//                //setGroupId groupId
//                groupId = "com.hennge.sankou"
//                //setArtifactId artifactId
//                artifactId = "sankouview"
//                version = "0.1.2"
//                //from components.java
//
//                pom {
//                    name = "SankouView"
//                    description = "A simple library for displaying open source license references. Based on Licensee."
//                    url = "https://github.com/HENNGE/sankouview"
//
//                    licenses {
//                        license {
//                            name = "The Apache License, Version 2.0"
//                            url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
//                        }
//                    }
//
//                    developers {
//                        developer {
//                            id = "charles-hennge"
//                            name = "Charles Bond"
//                            email = "charles.bond@hennge.com"
//                        }
//                    }
//                }
//
//                afterEvaluate {
//                    from(components["release"])
//                }
//            }
//        }
//
//        repositories {
//            mavenLocal()
//        }
//    }
//}