plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("org.springframework:spring-context:6.1.12")
    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.15.2")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation ("org.springframework:spring-aop:6.1.13")
    implementation ("org.aspectj:aspectjweaver:1.9.6")
    implementation ("org.springframework:spring-jdbc:6.1.13")
    implementation("org.postgresql:postgresql:42.7.4")


    compileOnly ("org.projectlombok:lombok:1.18.34")
    annotationProcessor ("org.projectlombok:lombok:1.18.34")

    testCompileOnly ("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.34")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}