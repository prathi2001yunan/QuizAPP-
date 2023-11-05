package com.example.newbutton

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.newbutton.ui.theme.OptionData
import com.example.newbutton.ui.theme.ViewModelClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                val view by viewModels<ViewModelClass>()
             SimpleRadioButtonComponent(
                 LocalContext.current,view.selectedOption,view.onOptionSelected,view.buttonEnable,
                  view.time, { view.timerRestart()}, {view.updateScore()},view.questionLength,{view.updateQuestionLength()},view.checkLength, view.correctAnswer)
                        }
                    }
                }
            }
            @SuppressLint("UnrememberedMutableState")
            @Composable
            fun SimpleRadioButtonComponent(
                context: Context,
                onSelected: MutableState<String>,
                onOptionSelected: MutableState<String>,
                buttonEnable: MutableState<Boolean>,
                timer: MutableState<Int>,
                timerRestart: () -> Unit,
                updateScore: () -> Unit,
                length: MutableState<Int>,
                updateLength: () -> Unit,
                checkLength:MutableState<Int>,
                correctAnswer: MutableState<Boolean>,
            ) {
                         LaunchedEffect(key1 = true) {
                        while (timer.value > 0) {
                            delay(1000L)
                            timer.value--
                            if(timer.value == 0) {
                                updateLength()
                                timerRestart()
                            }
                        }

                    }
                Spacer(modifier = Modifier.height(20.dp))
                QuizTimer(timer)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    QuizQuesDisplay(length,checkLength)
                    QuizOptionDisplay(context,onSelected,onOptionSelected,buttonEnable,correctAnswer,length)
                    Spacer(modifier = Modifier.height(10.dp))
                    QuizScreenButton(timerRestart,onOptionSelected,onSelected,buttonEnable,updateScore,updateLength,length,correctAnswer,checkLength)
                }
            }




@Composable
fun QuizQuesDisplay(length: MutableState<Int>,checkLength: MutableState<Int>) {
    Card(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp), colors = CardDefaults.cardColors(
            Color(0xFFE3E2E6)
        ), elevation = CardDefaults.cardElevation(defaultElevation = 40.dp)
    ) {
        val quesList= listOf<String>("Developers who write in Kotlin are called ?", "Kotlin are what type of language", "Kotlin is mainly used for ?")
        checkLength.value = quesList.size -1
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${length.value+1}) ${quesList[length.value]}",
                fontSize = 30.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 40.sp
            )
        }
    }
}

@Composable
fun QuizOptionDisplay(context: Context,
                      selectedOption: MutableState<String>,
                      onOptionSelected: MutableState<String>,
                      buttonEnable: MutableState<Boolean>,
                      correctAnswer: MutableState<Boolean>,
                      length: MutableState<Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        val optionList1= listOf<List<String>>(listOf<String>("Kotlin developer","Java developer","Python developer"),
         listOf("Statically typed programming language","Dynamically typed programming language","Both"),
            listOf("Android developing", "Web developing","Ios developing")
        )
        val answerList = listOf<String>("Kotlin developer","Statically typed programming language","Android developing")
        val optionList = optionList1[length.value]
        optionList.forEach() { text->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .shadow(20.dp)
                    .background(
                        Color(0xFFFAF8FC),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .selectable(
                        selected = (text == selectedOption.value),
                        onClick = {
                            selectedOption.value = text
                            onOptionSelected.value = text
                            buttonEnable.value = true
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                RadioButton(
                    selected = (text == selectedOption.value),
                    modifier = Modifier.padding(all = Dp(value = 8F)),
                    onClick = {
                        correctAnswer.value = answerList[length.value] == text
                        selectedOption.value = text
                        onOptionSelected.value = text
                      buttonEnable.value = true
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}

@Composable
fun QuizTimer(timer: MutableState<Int>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        Arrangement.End,
        Alignment.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp)
                .border(2.dp, Color.DarkGray, RoundedCornerShape(10.dp)),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {

            Text(
                text = timer.value.toString(),
                fontSize = 25.sp,
            )
        }
    }
}

@Composable
fun QuizScreenButton(timerRestart: () -> Unit,
                     onOptionSelected: MutableState<String>,
                     selectedOption: MutableState<String>,
                     buttonEnable: MutableState<Boolean>,
                     updateScore: () -> Unit,
                     updateLength: () -> Unit,
                     length: MutableState<Int>,
                     correctAnswer: MutableState<Boolean>,checkLength: MutableState<Int>
                     ) {
    Button(
        onClick = {
           timerRestart()
            onOptionSelected.value = ""
            buttonEnable.value = false
            if(correctAnswer.value) {
                updateScore()
            }
            selectedOption.value = ""
            updateLength()
                  }, enabled = buttonEnable.value, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 30.dp)
    ) {
        Text(
            text =if(checkLength.value == length.value) "Sumbit" else "Next",
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold
        )
    }
}








