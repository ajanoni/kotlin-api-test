plugins {
    java
    kotlin("jvm") version "1.4.32"
}

allprojects {
    repositories{
        mavenCentral()
        jcenter()
    }

    apply(plugin = "java")
    apply(plugin = "kotlin")


    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
        kotlinOptions.javaParameters = true
    }

    dependencies {
        implementation(kotlin("stdlib"))
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
