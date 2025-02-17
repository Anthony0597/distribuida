plugins {
    id("java")
    id("io.freefair.lombok") version "8.11"
}

group = "com.programacion.distribuida"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate:hibernate-core:6.6.3.Final")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("com.google.code.gson:gson:2.11.0")

}
