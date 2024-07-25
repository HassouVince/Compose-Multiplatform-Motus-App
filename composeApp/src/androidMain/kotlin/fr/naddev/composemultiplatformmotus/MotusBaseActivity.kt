package fr.naddev.composemultiplatformmotus

import android.os.Bundle
import androidx.activity.ComponentActivity
import org.koin.core.context.stopKoin
import setContext

abstract class MotusBaseActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContext(this)
    }

    override fun onDestroy() {
        stopKoin()
        super.onDestroy()
    }
}