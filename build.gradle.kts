plugins {
    id("java")
}

group = "kr.hqservice.mailbox"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.spigotmc", "spigot", "1.19.4-R0.1-SNAPSHOT")

    compileOnly("com.zaxxer", "HikariCP", "5.0.1")
    compileOnly("com.github.luben", "zstd-jni", "1.5.5-5")

    compileOnly("org.projectlombok", "lombok", "1.18.20")
    annotationProcessor("org.projectlombok", "lombok", "1.18.20")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter", "junit-jupiter")
    testImplementation("com.github.seeseemelk", "MockBukkit-v1.20", "3.9.0")
    testImplementation("com.github.luben", "zstd-jni", "1.5.5-5")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    test {
        useJUnitPlatform()
    }
    jar {
        archiveFileName.set("${rootProject.name}-${project.version}.jar")
        destinationDirectory.set(File("D:\\서버\\1.19.4 - 개발\\plugins"))
    }
}