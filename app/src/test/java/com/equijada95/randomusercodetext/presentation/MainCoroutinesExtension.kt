package com.equijada95.randomusercodetext.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutinesExtension: BeforeEachCallback, AfterEachCallback {

    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testCoroutineDispatcher)

    val testDispatcherProvider =
        object : DispatcherProvider {
            override fun default(): CoroutineDispatcher = testCoroutineDispatcher
            override fun io(): CoroutineDispatcher = testCoroutineDispatcher
            override fun main(): CoroutineDispatcher = testCoroutineDispatcher
            override fun unconfined(): CoroutineDispatcher = testCoroutineDispatcher
        }

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }

    fun runTest(testBody: suspend TestScope.() -> Unit) = testScope.runTest(testBody = testBody)
}