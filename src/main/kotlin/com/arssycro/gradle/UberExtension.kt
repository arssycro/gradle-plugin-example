package com.arssycro.gradle

import com.arssycro.gradle.operation.JunitOperation
import com.arssycro.gradle.operation.SpringOperation
import com.arssycro.gradle.operation.UberOperation

open class UberExtension {
    internal val operations: MutableList<UberOperation> = mutableListOf()

    fun junit() {
        operations.add(JunitOperation())
    }

    fun spring() {
        operations.add(SpringOperation())
    }
}