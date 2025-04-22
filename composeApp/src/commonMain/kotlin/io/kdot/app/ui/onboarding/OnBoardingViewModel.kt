package io.kdot.app.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kdot.app.designsystem.Resources
import kotlinx.coroutines.launch
/*import net.folivo.trixnity.olm.OlmAccount
import net.folivo.trixnity.olm.OlmInboundGroupSession
import net.folivo.trixnity.olm.OlmOutboundGroupSession
import net.folivo.trixnity.olm.OlmPkDecryption
import net.folivo.trixnity.olm.OlmPkEncryption
import net.folivo.trixnity.olm.OlmPkSigning
import net.folivo.trixnity.olm.OlmSession
import net.folivo.trixnity.olm.OlmUtility*/

class OnBoardingViewModel : ViewModel() {

    val onBoardingState = OnBoardingState(applicationName = Resources.String.title_app_name)


    fun olmTesting() = viewModelScope.launch {
       /* testAccountCreation()
//        testOutboundSessionCreation()
        testInboundSessionCreation()
        testGroupSessionEncryptionDecryption()
        testPkEncryptionDecryption()
        testPkSigning()
        testUtilityFunctions()*/
    }
/*
    private suspend fun testAccountCreation() {
        val aliceAccount = OlmAccount.create()
        val bobAccount = OlmAccount.create()

        aliceAccount.generateOneTimeKeys(1)
        bobAccount.generateOneTimeKeys(1)

        val aliceOutBoundSession = OlmSession.createOutbound(
            aliceAccount,
            bobAccount.identityKeys.curve25519,
            bobAccount.oneTimeKeys.curve25519.values.first()
        )


        val aliceToBobMessage = "hello bob"
        val aliceOutBoundMessage = aliceOutBoundSession.encrypt(aliceToBobMessage)


        val bobInboundSession = OlmSession.createInbound(
            bobAccount,
            bobAccount.oneTimeKeys.curve25519.values.first()
        )

        val message: String = bobInboundSession.decrypt(aliceOutBoundMessage)
        val res = aliceToBobMessage == message

        val bobOutboundSession = OlmSession.createOutbound(
            bobAccount,
            aliceAccount.identityKeys.curve25519,
            aliceAccount.oneTimeKeys.curve25519.values.first()
        )
        val bobToAliceMessage = "Hello Alice"
        val bobOutBoundMessage = bobOutboundSession.encrypt(bobToAliceMessage)

        val aliceInboundSession = OlmSession.createInbound(
            aliceAccount,
            aliceAccount.oneTimeKeys.curve25519.values.first()
        )
        val message2 = aliceInboundSession.decrypt(bobOutBoundMessage)
        val res2 = bobToAliceMessage == message2
    }

    private suspend fun testOutboundSessionCreation() {
        val account = OlmAccount.create()
        account.generateOneTimeKeys(1)
        val oneTimeKeys = account.oneTimeKeys
        val theirIdentityKey = oneTimeKeys.curve25519.keys.first()
        val theirOneTimeKey = oneTimeKeys.curve25519.values.first()

        val outbound = OlmSession.createOutbound(account, theirIdentityKey, theirOneTimeKey)
        val message = "Hello from outbound session!"
        val encrypted = outbound.encrypt(message)
        println("Outbound Encrypted: $encrypted")
    }

    private suspend fun testInboundSessionCreation() {
        val account = OlmAccount.create()
        val outbound = OlmSession.createOutbound(
            account,
            account.identityKeys.curve25519,
            account.oneTimeKeys.curve25519.values.first()
        )
        val message = outbound.encrypt("Secret message")

        val inbound = OlmSession.createInbound(account, message.cipherText)
        val decrypted = inbound.decrypt(message)
        println("Inbound Decrypted: $decrypted")
    }

    private suspend fun testGroupSessionEncryptionDecryption() {
        val outboundGroupSession = OlmOutboundGroupSession.create()
        val sessionKey = outboundGroupSession.sessionKey
        val encryptedGroupMessage = outboundGroupSession.encrypt("Group message")

        val inboundGroupSession = OlmInboundGroupSession.create(sessionKey)
        val decryptedGroupMessage = inboundGroupSession.decrypt(encryptedGroupMessage)
        println("Group message decrypted: $decryptedGroupMessage")
    }

    private suspend fun testPkEncryptionDecryption() {
        val decryption = OlmPkDecryption.create()
        val publicKey = decryption.publicKey

        val encryption = OlmPkEncryption.create(publicKey)
        val encrypted = encryption.encrypt("Hello PK")
        val decrypted = decryption.decrypt(encrypted)
        println("PK decrypted: $decrypted")
    }

    private suspend fun testPkSigning() {
        val signing =
            OlmPkSigning.create("your_private_signing_key_here") // Replace with actual key
        val signedMessage = signing.sign("Important message")
        println("Signed message: $signedMessage")
    }

    private suspend fun testUtilityFunctions() {
        val utility = OlmUtility.create()
        val hash = utility.sha256("hash me")
        println("SHA256 hash: $hash")

        try {
            utility.verifyEd25519("publicKey", "message", "signature")
            println("Signature verified!")
        } catch (e: Exception) {
            println("Signature verification failed: ${e.message}")
        }
    }*/
}
