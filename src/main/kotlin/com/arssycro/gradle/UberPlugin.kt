package com.arssycro.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class UberPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        val extension = project.extensions.create<UberExtension>("uber")

        afterEvaluate {
            extension.operations.forEach { it.apply(project) }
        }
    }
}