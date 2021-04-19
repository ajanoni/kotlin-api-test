group = "com.ajanoni.core"
version = "1.0-SNAPSHOT"

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
