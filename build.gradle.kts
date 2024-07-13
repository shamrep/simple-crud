import org.gradle.internal.impldep.com.fasterxml.jackson.core.JsonPointer.compile

plugins {
    id("java")
    id("application")
//    id("war")
}

group = "org.simplecrud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")

    implementation("org.postgresql:postgresql:42.7.1")
//    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")

    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.25")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:10.1.25")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "org.simplecrud.SimpleCrudApp"
}