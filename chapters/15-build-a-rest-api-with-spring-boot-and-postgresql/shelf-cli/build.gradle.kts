plugins {
    application
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

dependencies {
    implementation(project(":shelf-core"))
}

application {
    mainClass = "shelf.Main"
}
