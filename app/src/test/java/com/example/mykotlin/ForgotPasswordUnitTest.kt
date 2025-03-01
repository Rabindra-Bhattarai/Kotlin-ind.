package com.example.mykotlin


import com.example.mykotlin.repository.ForgotPasswordImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.Captor

class ForgotPasswordUnitTest {

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<Void>

    private lateinit var forgotPasswordRepo: ForgotPasswordImpl

    @Captor
    private lateinit var captor: ArgumentCaptor<OnCompleteListener<Void>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        forgotPasswordRepo = ForgotPasswordImpl(mockAuth)
    }

    @Test
    fun testForgotPassword_Successful() {
        val email = "test@example.com"
        var expectedResult = "Initial Value" // Define the initial value

        // Mocking task to simulate a successful password reset
        `when`(mockTask.isSuccessful).thenReturn(true)
        `when`(mockAuth.sendPasswordResetEmail(email)).thenReturn(mockTask)

        // Define a callback that updates the expectedResult
        val callback = { success: Boolean, message: String? ->
            expectedResult = message ?: "Callback message is null"
        }

        // Call the function under test
        forgotPasswordRepo.forgotPassword(email, callback)

        // Capture the callback
        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        // Assert the result
        assertEquals("Password reset email sent.", expectedResult)
    }

    @Test
    fun testForgotPassword_Failure() {
        val email = "test@example.com"
        var expectedResult = "Initial Value" // Define the initial value

        // Mocking task to simulate a failed password reset
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(Exception("Failed to send email"))
        `when`(mockAuth.sendPasswordResetEmail(email)).thenReturn(mockTask)

        // Define a callback that updates the expectedResult
        val callback = { success: Boolean, message: String? ->
            expectedResult = message ?: "Callback message is null"
        }

        // Call the function under test
        forgotPasswordRepo.forgotPassword(email, callback)

        // Capture the callback
        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        // Assert the result
        assertEquals("Failed to send email", expectedResult)
    }
}
