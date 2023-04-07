plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}

dependencies {
    implementation("com.high-mobility:hmkit-fleet:0.7.0")
    // 13.2.0 not found, so using 13.1.1 (13.2.0 is released, but not on mavenCentral/Sona as of 10.3.2023)
    implementation("com.high-mobility:hmkit-auto-api:13.1.1")
    implementation("com.high-mobility:hmkit-crypto-telematics:0.1")


    // jwt
    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}