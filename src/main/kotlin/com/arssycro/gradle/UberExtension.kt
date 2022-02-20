package com.arssycro.gradle

import com.arssycro.gradle.operation.JunitOperation
import com.arssycro.gradle.operation.SpringOperation
import com.arssycro.gradle.operation.UberOperation
import org.gradle.api.Action
import org.gradle.api.Project

open class UberExtension {
    internal val operations: MutableList<UberOperation> = mutableListOf()

    fun junit() {
        operations.add(JunitOperation())
    }

    fun spring() {
        operations.add(SpringOperation())
    }

    companion object {
        fun Project.uber(action: Action<UberExtension>) {
            extensions.configure("uber", action)
        }
    }
}