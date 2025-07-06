package dev.afalabarce.mangaref.presentation.ui.features.common

import dev.afalabarce.mangaref.core.common.extensions.Empty

fun String.asKiValue(): Double = try{
        this.replace(".", String.Empty)
            .replace(",", String.Empty)
            .replace(" ", String.Empty)
            .lowercase()
            .let { baseValue ->
            when {
                baseValue.contains("septillion") -> baseValue.replace("septillion", String.Empty).toDouble() * 10000000000000
                baseValue.contains("sextillion") -> baseValue.replace("sextillion", String.Empty).toDouble() * 1000000000000
                baseValue.contains("quintillion") -> baseValue.replace("quintillion", String.Empty).toDouble() * 100000000000
                baseValue.contains("quadrillion") -> baseValue.replace("quadrillion", String.Empty).toDouble() * 10000000000
                baseValue.contains("trillion") -> baseValue.replace("trillion", String.Empty).toDouble() * 1000000000
                baseValue.contains("billion") -> baseValue.replace("billion", String.Empty).toDouble() * 100000000
                baseValue.contains("million") -> baseValue.replace("million", String.Empty).toDouble() * 1000000
                baseValue.contains("unknown") -> Double.NaN
                else -> baseValue.toDouble()
            }
        }
    }catch (_: Exception){
        0.0
    }