package com.skjline.fitness.feature.registration.model

interface Input

data class CredentialInput(val username: String, val password: String) : Input
data class ProfileInput(val name: String, val age: Int) : Input
data class FitnessInput(val ftp: Int, val weight: Int) : Input
data class AthleteProfileInput(val name: String, val age: Int, val ftp: Int, val weight: Int) : Input
data class ProcessComplete(val isReg: Boolean, val isSucceed: Boolean) : Input

data class RegistrationProfileInput(
    val username: String, val password: String,
    val fullname: String, val age: Int,
    val ftp: Int, val weight: Int,
) : Input
