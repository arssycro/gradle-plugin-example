package com.arssycro.gradle.operation

import io.spring.gradle.dependencymanagement.internal.dsl.StandardDependencyManagementExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByName

class SpringOperation : UberOperation {
    override fun apply(project: Project) {
        project.apply(plugin = "io.spring.dependency-management")

        project.extensions.getByName<StandardDependencyManagementExtension>("dependencyManagement").apply {
            imports {
                mavenBom("org.springframework:spring-framework-bom:5.3.16")
            }
        }
    }
}