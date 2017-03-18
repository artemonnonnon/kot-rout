package common.utils

inline infix fun <T> T?.ifNull(other: () -> T): T = if (this == null) other() else this
infix fun <T> T?.ifNull(other: T): T = if (this == null) other else this
fun isNotNull(a: Any?): Boolean = a != null
fun isNull(a: Any?): Boolean = a == null
fun <T> eitherOr(vararg options: T?): T? = options.firstOrNull(::isNotNull)