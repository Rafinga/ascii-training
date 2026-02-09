plugins {
    id("java")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.bytedeco/javacv
    implementation("org.bytedeco:javacv-platform:1.5.12")
    // https://mvnrepository.com/artifact/org.projectlombok/lombok

    implementation("org.bytedeco:opencv-platform:4.11.0-1.5.12")
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    // Define the main class for the application.
    mainClass = "org.example.Main"
}

tasks.test {
    useJUnitPlatform()
}