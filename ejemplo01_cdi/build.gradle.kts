plugins {
    id("java")
}

group = "com.programacion.distribuida"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jboss.weld.se:weld-se-core:5.1.3.Final")

}


sourceSets {
    main {
        output.setResourcesDir( file("${buildDir}/classes/java/main") )
    }
}