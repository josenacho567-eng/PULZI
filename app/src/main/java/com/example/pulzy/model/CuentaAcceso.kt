package com.example.pulzy.model

import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class CuentaAcceso(val id: Int, val correo: String, clave: CharArray? = null) {
    private val sal = ByteArray(16).also { SecureRandom().nextBytes(it) }
    private fun derivar(clave: CharArray): ByteArray {
        val spec = PBEKeySpec(clave, sal, 600000, 256)
        return try {
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).encoded
        } finally {
            spec.clearPassword()
        }
    }

    private val claveHash = clave?.let(::derivar)
    var activa = claveHash != null
        private set

    fun validarClave(clave: String) = activa && MessageDigest.isEqual(claveHash, derivar(clave.toCharArray()))
    fun desactivar() { activa = false }
}
