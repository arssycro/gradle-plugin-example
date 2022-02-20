package com.arssycro.gradle.operation

import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByName

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