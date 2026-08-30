package com.skjline.fitness.data.storage.model

fun <I, M> I.mapTo(block: (I) -> M): M = block(this)
