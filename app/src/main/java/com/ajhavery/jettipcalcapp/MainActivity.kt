package com.ajhavery.jettipcalcapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajhavery.jettipcalcapp.components.InputField
import com.ajhavery.jettipcalcapp.ui.theme.JetTipCalcAppTheme
import com.ajhavery.jettipcalcapp.util.calculateTotalPerPerson
import com.ajhavery.jettipcalcapp.util.calculateTotalTip
import com.ajhavery.jettipcalcapp.widgets.RoundIconButton

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}


@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetTipCalcAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
fun TopHeader(totalPerPerson: Double = 133.34) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Total Per Person", style = MaterialTheme.typography.subtitle1)
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "\$$total",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun MainContent() {
    val splitByState = remember {
        mutableStateOf(1)
    }

    val tipAmountState = remember {
        mutableStateOf(0.0)
    }

    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }

    Column(modifier = Modifier.padding(all = 12.dp)) {
        BillForm(
            splitByState = splitByState,
            tipAmountState = tipAmountState,
            totalPerPersonState = totalPerPersonState
        ) { }
    }
}

// Billform will host the input field
@ExperimentalComposeUiApi
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    onValChange: (String) -> Unit = {}
) {
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    val tipPercentage = (sliderPositionState.value * 100).toInt()

    TopHeader(totalPerPersonState.value)

    Surface(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(
                valueState = totalBillState,
                labelId = "Enter bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                    // hide keyboard on pressing next if valid state
                }
            )

            Row(
                modifier = modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Split",
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(120.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    RoundIconButton(
                        imageVector = Icons.Default.Remove,
                        onClick = {
                            splitByState.value =
                                if (splitByState.value > 1) splitByState.value - 1 else 1
                            totalPerPersonState.value =
                                calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage,
                                    splitBy = splitByState.value
                                )
                        })
                    Text(
                        text = splitByState.value.toString(),
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = 10.dp, end = 10.dp)
                    )
                    RoundIconButton(
                        imageVector = Icons.Default.Add,
                        onClick = {
                            splitByState.value++
                            totalPerPersonState.value =
                                calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage,
                                    splitBy = splitByState.value
                                )
                        })
                }
            }
            Row(modifier = modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                Text(
                    text = "Tip",
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(200.dp))

                Text(
                    text = "$ ${tipAmountState.value}",
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "$tipPercentage %")
                Spacer(modifier = Modifier.height(14.dp))
                // Slider
                Slider(
                    value = sliderPositionState.value,
                    onValueChange = { newVal ->
                        sliderPositionState.value = newVal
                        tipAmountState.value =
                            calculateTotalTip(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage
                            )
                        totalPerPersonState.value =
                            calculateTotalPerPerson(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage,
                                splitBy = splitByState.value
                            )
                    }, modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    steps = 5,
                    onValueChangeFinished = {

                    }
                )
            }
        }
    }
}


//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTipCalcAppTheme {
        MyApp {

        }
    }
}