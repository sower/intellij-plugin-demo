fun properties(key: String) = providers.gradleProperty(key).get()
// fun environment(key: String) = providers.environmentVariable(key)

plugins {
    id("java")
    alias(libs.plugins.intelliJ)
    alias(libs.plugins.kotlin)
    // alias(libs.plugins.changelog) // Gradle Changelog Plugin
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()  // "-" + properties("pluginSinceBuild")

// repositories {
//    mavenLocal()
//    maven { name "Alibaba" ; url "https://maven.aliyun.com/repository/public" }
//    maven { name "Bstek" ; url "https://nexus.bsdn.org/content/groups/public/" }
//    mavenCentral()
//}

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog
dependencies {
    // testImplementation(libs.junit)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    // localPath.set("D:\\Applications\\Portable\\Editor\\idea")
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))
    updateSinceUntilBuild.set(true)

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        options.encoding = "UTF-8"
    }
    withType<JavaExec> {
        // 解决运行时控制台中文乱码
        jvmArgs = listOf(
            "-Dfile.encoding=UTF-8",
            "-Dsun.stdout.encoding=UTF-8",
            "-Dsun.stderr.encoding=UTF-8"
        )
    }
    // withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    //    kotlinOptions.jvmTarget = "17"
    // }

    patchPluginXml {
        sinceBuild.set("222")
        // untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

tasks.register("encodeTest") {
    group = "Other"
    description = "文件编码测试task"
    println("自定义Task执行")
}
