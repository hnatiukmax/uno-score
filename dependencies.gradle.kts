enum class Lib(val version: String, val lib: String) {
    AppCompat(version = "1.4.2", lib = "androidx.appcompat:appcompat"),
    Material(version = "1.6.1", lib = "com.google.android.material:material"),
    //Ktx
    FragmentKtx(version = "1.5.0", lib = "androidx.fragment:fragment-ktx"),
    CoreKtx(version = "1.8.0", lib = "androidx.core:core-ktx"),
    //DI
    HiltAndroid(version = "2.42", lib = "com.google.dagger:hilt-android"),
    HiltCompiler(version = "2.42", lib = "com.google.dagger:hilt-compiler"),
    //Navigation
    Cicerone(version = "7.1", lib = "com.github.terrakok:cicerone");

    val libName get() = this.toString().decapitalize()
}

Lib.values()
    .map { it to "${it.lib}:${it.version}" }
    .forEach {
        project.extra[it.first.libName] = it.second
    }