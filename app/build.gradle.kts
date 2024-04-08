plugins {
    java
    alias(libs.plugins.shadow)
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation(libs.guava)
    implementation(libs.picocli)
    implementation(libs.jsoup)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks {
    shadowJar {
        archiveBaseName = "gt-cli"
        archiveClassifier = null
        manifest {
            attributes["Main-Class"] = "com.github.navust.gtcli.GtCli"
        }
    }
}
