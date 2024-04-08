plugins {
    application
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

application {
    mainClass = "com.github.navust.gtcli.GtCli"
    applicationName = "gt-cli"
}

tasks {
    shadowJar {
        archiveFileName = "gt-cli.jar"
        minimize()
        manifest {
            attributes["Main-Class"] = "com.github.navust.gtcli.GtCli"
        }
    }

    shadowDistTar {
        archiveFileName = "gt-cli.tar"
    }

    shadowDistZip {
        archiveFileName = "gt-cli.zip"
    }
}
