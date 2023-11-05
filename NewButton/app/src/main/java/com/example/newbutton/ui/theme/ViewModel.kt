package com.example.newbutton.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelClass: ViewModel() {
     var time = mutableStateOf(60)
    val selectedOption = mutableStateOf("")
    val onOptionSelected = mutableStateOf("")
    val buttonEnable = mutableStateOf(false)
    val score = mutableStateOf(0)
    val correctAnswer = mutableStateOf(false)
    val questionLength = mutableStateOf(0)
    val checkLength = mutableStateOf(0)

    fun updateScore(){
        score.value = score.value + 10
    }
    fun updateQuestionLength(){
        questionLength.value++
    }
    fun timerRestart() {
            time.value = 60
        }

}