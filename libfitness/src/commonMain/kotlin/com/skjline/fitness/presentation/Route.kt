package com.skjline.fitness.presentation

import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY

sealed interface Route

data object DismissModal: Route
data object DismissActivity: Route

data object RegistrationRoute: Route
data class SessionRoute(val path: String): Route
data class MainRoute(val source: String = EMPTY): Route

data class ShowModal(
    val title: String = EMPTY,
    val message: String = EMPTY
): Route
