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
    implementation("org.springframework:spring-orm:6.1.13")
    implementation ("org.springframework:spring-aop:6.1.13")
    implementation("org.springframework:spring-jdbc:6.1.13")

    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.15.2")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.2")

    implementation ("org.aspectj:aspectjweaver:1.9.6")

    implementation("org.apache.logging.log4j:log4j-api:2.24.0")
    implementation("org.apache.logging.log4j:log4j-core:2.24.0")

    implementation("org.postgresql:postgresql:42.7.4")

    implementation("org.hibernate.orm:hibernate-core:6.6.1.Final")
    implementation ("jakarta.persistence:jakarta.persistence-api:3.0.0")
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")

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