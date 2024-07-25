package di

fun appModule() = listOf(
    commonModule,
    viewModule,
    remoteModule,
    localModule,
    usesCasesModule,
    repositoryModule
)