plugins {
    id("java")
//    id("application")
    id("io.freefair.lombok") version "8.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.programacion.distribuida"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    // HTTP
    implementation("io.helidon.webserver:helidon-webserver:4.1.4")
    // JSON
    implementation("com.google.code.gson:gson:2.10.1")
    //CDI
    implementation("org.jboss.weld.se:weld-se-core:5.1.3.Final")
    //JPA
    implementation("org.hibernate.orm:hibernate-core:6.6.3.Final")
    // DB
    implementation("org.postgresql:postgresql:42.7.4")

    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")


}

sourceSets {
    main {
        output.setResourcesDir( file("${buildDir}/classes/java/main") )
    }
}

tasks.jar {
    manifest {
        attributes(
            mapOf("Main-Class" to "com.programacion.distribuida.PrincipalRest",
                "Class-Path" to configurations.runtimeClasspath
                    .get()
                    .joinToString(separator = " ") { file ->
                        "${file.name}"
                    })
        )
    }
}