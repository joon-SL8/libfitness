package com.skjline.fitness.data.storage.model

data class TAndCAgreement(
    val type: String = "",
    val isAgreed: Boolean = false,
    val dateAgreed: Long = 0L
) {
    public companion object {
        fun createAgreement() : TAndCAgreement {
            return TAndCAgreement()
        }
    }
}
