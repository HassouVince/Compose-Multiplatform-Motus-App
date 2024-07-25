package di

import data.datasource.SqlDriverFactory
import data.datasource.game.GameLocalDataSource
import data.datasource.game.GameLocalDataSourceImpl
import data.datasource.words.WordsLocalDataSource
import data.datasource.words.WordsLocalDataSourceImpl
import data.datasource.words.WordsRemoteDataSource
import data.datasource.words.WordsRemoteDataSourceImpl
import data.repositories.GameRepository
import data.repositories.GameRepositoryImpl
import data.repositories.WordsRepository
import data.repositories.WordsRepositoryImpl
import domain.usecases.game.ClearGameUseCase
import domain.usecases.game.GetGameStateUseCase
import domain.usecases.game.SaveGameUseCase
import domain.usecases.game.UpdateGameStateUseCase
import domain.usecases.game.VerifyWordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ui.viewmodels.GameViewModel
import kotlin.coroutines.CoroutineContext

val commonModule = module {
    factory<CoroutineContext> { Dispatchers.IO }
}

val viewModule = module {
    viewModelOf(::GameViewModel)
}

val usesCasesModule = module {
    single { GetGameStateUseCase(get(), get(), get()) }
    single { UpdateGameStateUseCase(get(), get(), get()) }
    single { SaveGameUseCase(get(), get()) }
    single { VerifyWordUseCase(get()) }
    single { ClearGameUseCase(get(), get(), get()) }
}

val repositoryModule = module {
    single<GameRepository> { GameRepositoryImpl(get()) }
    single<WordsRepository> { WordsRepositoryImpl(get(), get()) }
}

val remoteModule = module {
    single<WordsRemoteDataSource> { WordsRemoteDataSourceImpl(get()) }
}

val localModule = module {
    single<SqlDriverFactory> { SqlDriverFactory() }
    single<GameLocalDataSource> { GameLocalDataSourceImpl(get()) }
    single<WordsLocalDataSource> { WordsLocalDataSourceImpl(get()) }
}