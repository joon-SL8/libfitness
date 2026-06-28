package com.skjline.fitness.data.storage.input

interface Input

data class CredentialDataInput(val username: String, val password: String) : Input

data class UserProfileInput(val name: String, val age: Int) : Input

data class FitnessInput(val ftp: Int, val weight: Int) : Input

data class AthleteProfileDataInput(val name: String, val age: Int, val ftp: Int, val weight: Int) : Input
