package com.abdul.simplesharedflowdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        consumer()
    }
}

// Step1: Producer produces/emits the values
private fun producer(): Flow<Int> { // Shared Flow<In> can be used.
    //val mutableSharedFlow = MutableSharedFlow<Int>()
    val mutableSharedFlow = MutableSharedFlow<Int>(2)
    GlobalScope.launch {
        val list = listOf<Int>(1, 2, 3, 4, 5)
        list.forEach {
            mutableSharedFlow.emit(it)
            delay(1000)
        }
    }
    return mutableSharedFlow
}

// Step2: Consumer consumes/collects the values
private fun consumer() {
    GlobalScope.launch {
        val result = producer()
        result.collect {
            Log.d("SharedFlow", "Item1 - $it")
        }
    }
    GlobalScope.launch {
        val result = producer()
        delay(2500)
        result.collect {
            Log.d("SharedFlow", "Item2 - $it")
        }
    }
}