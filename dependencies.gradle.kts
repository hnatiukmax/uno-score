enum class Lib(val version: String, val lib: String) {
    AppCompat(version = "1.8.0", lib = "androidx.core:core-ktx"),
    CoreKtx(version = "1.8.0", lib = "androidx.core:core-ktx"),
    Material(version = "1.6.1", lib = "com.google.android.material:material");

    val libName get() = this.toString().decapitalize()
}

Lib.values()
    .map { it to "${it.lib}:${it.version}" }
    .forEach {
        project.extra[it.first.libName] = it.second
    }