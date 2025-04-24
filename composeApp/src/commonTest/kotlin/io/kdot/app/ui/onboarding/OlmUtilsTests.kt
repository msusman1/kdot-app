import kotlinx.coroutines.test.runTest
import net.folivo.trixnity.crypto.core.decryptAes256Ctr
import net.folivo.trixnity.crypto.core.encryptAes256Ctr
import net.folivo.trixnity.olm.OlmAccount
import net.folivo.trixnity.olm.OlmInboundGroupSession
import net.folivo.trixnity.olm.OlmOutboundGroupSession
import net.folivo.trixnity.olm.OlmSession
import net.folivo.trixnity.utils.toByteArray
import net.folivo.trixnity.utils.toByteArrayFlow
import kotlin.test.Test
import kotlin.test.assertEquals

class OlmUtilsTest {


    @Test
    fun tes() {
        val number = 23
        assertEquals(number, 3 + 20)
    }

    @Test
    fun testOneToOneChat() = runTest {
        val aliceAccount = OlmAccount.create()
        val bobAccount = OlmAccount.create()

        // Bob prepares a one-time key for Alice
        bobAccount.generateOneTimeKeys(1)
        val bobsOneTimeKey = bobAccount.oneTimeKeys.curve25519.values.first()
        val bobsIdentityKey = bobAccount.identityKeys.curve25519

        // Alice creates an outbound session to Bob
        val aliceOutboundSession = OlmSession.createOutbound(
            aliceAccount,
            bobsIdentityKey,
            bobsOneTimeKey
        )

        val aliceToBobMessage = "hello bob"
        val encryptedMessageToBob = aliceOutboundSession.encrypt(aliceToBobMessage)


        // Bob creates an inbound session from Alice's message
        val bobInboundSession = OlmSession.createInbound(
            bobAccount,
            encryptedMessageToBob.cipherText
        )

        val decryptedFromAlice = bobInboundSession.decrypt(encryptedMessageToBob)



        assertEquals(aliceToBobMessage, decryptedFromAlice)

        // Bob sends message back using same session
        val bobToAliceMessage = "Hello Alice"
        val encryptedMessageToAlice = bobInboundSession.encrypt(bobToAliceMessage)

        // Alice can decrypt using the same outbound session
        val decryptedFromBob = aliceOutboundSession.decrypt(encryptedMessageToAlice)
        assertEquals(bobToAliceMessage, decryptedFromBob)
    }


    @Test
    fun testGroupChat() = runTest {
        val outBoutGroupSession = OlmOutboundGroupSession.create()
        val sessionId = outBoutGroupSession.sessionId
        val sessionKey = outBoutGroupSession.sessionKey
        val messageIndex = outBoutGroupSession.messageIndex

        val inBoundGroupSession = OlmInboundGroupSession.create(sessionKey)
        val message = "Welcome to pakistan"
        val en = outBoutGroupSession.encrypt(message)
        val de = inBoundGroupSession.decrypt(en)
        assertEquals(message, de.message)

    }

    @Test
    fun shouldEncryptAndDecrypt() = runTest {
        val key = ByteArray(32) { (it + 1).toByte() }
        val nonce = ByteArray(8) { (it + 1).toByte() }
        val initialisationVector = nonce + ByteArray(7) + ByteArray(1) { (0xff).toByte() }

        val fina = "hello".encodeToByteArray().toByteArrayFlow()
            .encryptAes256Ctr(key, initialisationVector)
            .decryptAes256Ctr(key, initialisationVector).toByteArray()
            .decodeToString()
        assertEquals("hello", fina)

    }

}