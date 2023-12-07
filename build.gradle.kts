plugins {
    kotlin("jvm") version "1.9.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }

}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}


dependencies {

    implementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}