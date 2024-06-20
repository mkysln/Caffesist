package com.aslansoft.caffesist.classes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class QRCodeViewModel : ViewModel(){
    private val _qrResult = mutableStateOf("")
    val qrResult: State<String> get() = _qrResult
    fun setQRResult(value: String){
        _qrResult.value = value
    }
    fun resetQRResult(){
        _qrResult.value = ""
    }
}