package com.myapp.revery
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.nio.charset.StandardCharsets
import java.util.Date

class Authentication {
    // Convert bytes to hex which is needed PBKDF2WithHmacSHA256 algorithm
    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = "0123456789abcdef"
        val hex = StringBuilder(2 * bytes.size)
        for (b in bytes) {
            val octet = b.toInt() and 0xFF
            hex.append(hexChars[octet shr 4])
            hex.append(hexChars[octet and 0x0F])
        }
        return hex.toString()
    }

    // Use the publicKey, secretKey from revery along with current timestamp to create
    // credentials used in API requests/post calls.
    fun getAuthenticationHeader(publicKey: String, secretKey: String): Map<String, String> {
        val time = (Date().time / 1000).toString()
        //val time = "1709519121" // OneTimeCode expected: 518d283daccae400accac925eb540c4bf9fbbec97433dcaa070e300d0e3f677e
        val salt = time.toByteArray(StandardCharsets.UTF_8)
        val iterations = 128
        val keyLength = 32

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec: KeySpec = PBEKeySpec(secretKey.toCharArray(), salt, iterations, keyLength * 8)
        val tmp = factory.generateSecret(spec)
        val derivedKey = tmp.encoded

        val derivedKeyHex = bytesToHex(derivedKey)

        return mapOf(
            "public_key" to publicKey,
            "one_time_code" to derivedKeyHex,
            "timestamp" to time
        )
    }
}