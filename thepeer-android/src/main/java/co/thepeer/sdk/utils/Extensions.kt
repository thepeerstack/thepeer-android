package co.thepeer.sdk.utils

import java.math.BigDecimal
import java.text.DecimalFormat

fun String.getLastPart() = this.split(".")[1]
fun String.getFirstPart() = this.split(".")[0]