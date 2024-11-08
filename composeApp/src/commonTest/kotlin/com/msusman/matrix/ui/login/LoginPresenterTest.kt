package com.msusman.matrix.ui.login

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import com.msusman.matrix.architecture.AsyncData
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class LoginPresenterTest {

    @Test
    fun testPresenterInitialState() = runTest {
        val presenter = LoginPresenter()
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            assertEquals(
                expected = defaultAccountProvider,
                actual = initialState.accountProvider
            )
            assertEquals(
                expected = LoginFormState.Default,
                actual = initialState.formState
            )
            assertEquals(
                expected = AsyncData.Uninitialized,
                actual = initialState.loginResultState
            )
            assertFalse(initialState.submitEnabled)
        }

    }

    // Test case for setting the username
    @Test
    fun testSetUsernamePasswordEventsUpdatesFormState() = runTest {
        val presenter = LoginPresenter()
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            initialState.eventSink(LoginEvent.setUsername("testUser"))
            val updatedState = awaitItem()
            assertEquals(
                expected = "testUser",
                actual = updatedState.formState.username
            )
            assertFalse(updatedState.submitEnabled)
            initialState.eventSink(LoginEvent.setPassword("testPassword"))
            val updatedState2 = awaitItem()
            assertEquals(
                expected = "testPassword",
                actual = updatedState2.formState.password
            )
            assertTrue(updatedState2.submitEnabled)
        }
    }

    @Test
    fun testSubmitWithInvalidCredentials() = runTest {
        val presenter = LoginPresenter()
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            initialState.eventSink.invoke(LoginEvent.setUsername("testUser"))
            initialState.eventSink.invoke(LoginEvent.setPassword("testPassword"))
            skipItems(1)
            val loginAndPasswordState = awaitItem()
            loginAndPasswordState.eventSink.invoke(LoginEvent.Submit)
            val loadingState = awaitItem()
            val finalState = awaitItem()
            assertTrue(finalState.loginResultState.isFailure())
        }
    }

    @Test
    fun testSubmitWithValidCredentials() = runTest {
        val presenter = LoginPresenter()
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            initialState.eventSink.invoke(LoginEvent.setUsername("testUser"))
            initialState.eventSink.invoke(LoginEvent.setPassword("testUser"))
            skipItems(1)
            val loginAndPasswordState = awaitItem()
            loginAndPasswordState.eventSink.invoke(LoginEvent.Submit)
            val loadingState = awaitItem()
            val finalState = awaitItem()
            assertTrue(finalState.loginResultState.isSuccess())
        }
    }

    @Test
    fun testResetEvent() = runTest {
        val presenter = LoginPresenter()
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            initialState.eventSink.invoke(LoginEvent.setUsername("testUser"))
            initialState.eventSink.invoke(LoginEvent.setPassword("testpasss"))
            skipItems(1)
            val loginAndPasswordState = awaitItem()
            loginAndPasswordState.eventSink.invoke(LoginEvent.Submit)
            val loadingState = awaitItem()
            val finalState = awaitItem()
            assertTrue(finalState.loginResultState.isFailure())
            initialState.eventSink.invoke(LoginEvent.Reset)
            val resetState = awaitItem()
            assertTrue(resetState.loginResultState.isUninitialized())
        }
    }
}