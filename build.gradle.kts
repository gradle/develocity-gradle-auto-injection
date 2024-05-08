plugins {
    groovy
    `jvm-test-suite`
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useSpock("2.3-groovy-3.0")

            dependencies {
                implementation(gradleTestKit())
                implementation("io.ratpack:ratpack-groovy-test:1.9.0") {
                    exclude(group = "org.codehaus.groovy", module = "groovy-all")
                }
                implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-smile:2.17.0")
            }
        }
    }
}
