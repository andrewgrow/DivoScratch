package divo.scratch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform