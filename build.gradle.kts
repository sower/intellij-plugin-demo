fun properties(key: String) = providers.gradleProperty(key).get()
// fun environment(key: String) = providers.environmentVariable(key)

plugins {
    id("java")
    // alias(libs.plugins.intelliJ)
    alias(libs.plugins.intelliJPlatform)
    alias(libs.plugins.kotlin)
    // alias(libs.plugins.changelog)
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()  // "-" + properties("pluginSinceBuild")

// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain(21)
}

repositories {

    intellijPlatform {
        defaultRepositories()
    }
}

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog
dependencies {
    // testImplementation(libs.junit)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        create(
            providers.gradleProperty("platformType"),
            providers.gradleProperty("platformVersion")
        )

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })

        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
        plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })

        instrumentationTools()
        pluginVerifier()
        zipSigner()
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
// intellij {
//     // localPath.set("D:\\Applications\\Portable\\Editor\\idea")
//     pluginName.set(properties("pluginName"))
//     version.set(properties("platformVersion"))
//     type.set(properties("platformType"))
//     updateSinceUntilBuild.set(true)
//
//     // plugins.set(listOf(/* Plugin Dependencies */))
// }

tasks {
    // Set the JVM compatibility versions
    // withType<JavaCompile> {
    //     sourceCompatibility = "17"
    //     targetCompatibility = "17"
    //     options.encoding = "UTF-8"
    // }
    withType<JavaExec> {
        // 解决运行时控制台中文乱码
        jvmArgs = listOf(
            "-Dfile.encoding=UTF-8",
            "-Dsun.stdout.encoding=UTF-8",
            "-Dsun.stderr.encoding=UTF-8"
        )
    }

    patchPluginXml {
        sinceBuild.set("222")
        // untilBuild.set("242.*")
    }
}

tasks.register("encodeTest") {
    group = "Other"
    description = "文件编码测试task"
    println("自定义Task执行")
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        version = providers.gradleProperty("pluginVersion")

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        // description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
        //     val start = "<!-- Plugin description -->"
        //     val end = "<!-- Plugin description end -->"
        //
        //     with(it.lines()) {
        //         if (!containsAll(listOf(start, end))) {
        //             throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
        //         }
        //         subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
        //     }
        // }

        // val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        // changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
        //     with(changelog) {
        //         renderItem(
        //             (getOrNull(pluginVersion) ?: getUnreleased())
        //                 .withHeader(false)
        //                 .withEmptySections(false),
        //             Changelog.OutputType.HTML,
        //         )
        //     }
        // }

        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
            // untilBuild = providers.gradleProperty("pluginUntilBuild")
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        // channels = providers.gradleProperty("pluginVersion")
        //     .map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}