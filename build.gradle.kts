plugins {
    kotlin("jvm") version "1.5.10"
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

open class UberExtension {
    internal var junit: Boolean = false

    fun junit() {
        junit = true
    }
}

class UberPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        val extension = extensions.create("uber", UberExtension::class.java)

        afterEvaluate {
            if (extension.junit) {
                tasks.getByName<Test>("test").apply {
                    useJUnitPlatform()
                }

                val testDependencies = configurations.getByName("testImplementation").dependencies
                gradle.addListener(object : DependencyResolutionListener {
                    override fun beforeResolve(dependencies: ResolvableDependencies) {
                        testDependencies.add(project.dependencies.create("org.junit.jupiter:junit-jupiter-api:5.8.1"))
                        testDependencies.add(project.dependencies.create("org.junit.jupiter:junit-jupiter-engine:5.8.1"))
                        gradle.removeListener(this)
                    }

                    override fun afterResolve(dependencies: ResolvableDependencies) {}
                })
            }
        }
    }
}

apply<UberPlugin>()

configure<UberExtension> {
    junit()
}