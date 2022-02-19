import Build_gradle.UberExtension.Companion.uber

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

interface UberOperation {
    fun apply(project: Project)
}

class JunitOperation : UberOperation {
    override fun apply(project: Project) {
        project.tasks.getByName<Test>("test").apply {
            useJUnitPlatform()
        }

        val testDependencies = project.configurations.getByName("testImplementation").dependencies
        project.gradle.addListener(object : DependencyResolutionListener {
            override fun beforeResolve(dependencies: ResolvableDependencies) {
                testDependencies.add(project.dependencies.create("org.junit.jupiter:junit-jupiter-api:5.8.1"))
                testDependencies.add(project.dependencies.create("org.junit.jupiter:junit-jupiter-engine:5.8.1"))
                project.gradle.removeListener(this)
            }

            override fun afterResolve(dependencies: ResolvableDependencies) {}
        })
    }
}

open class UberExtension {
    internal val operations: MutableList<UberOperation> = mutableListOf()

    fun junit() {
        operations.add(JunitOperation())
    }

    companion object {
        fun Project.uber(): UberExtension {
            return extensions.create("uber", UberExtension::class.java)
        }

        fun Project.uber(block: UberExtension.() -> Unit): UberExtension {
            return extensions.getByName<UberExtension>("uber").apply(block)
        }
    }
}

class UberPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        val extension = uber()

        afterEvaluate {
            extension.operations.forEach { it.apply(project) }
        }
    }
}

apply<UberPlugin>()

uber {
    // If you uncomment the following line, the example test class will no longer compile.
    junit()
}