package com.example.projettdm.reservation.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DateTimePicker(
    onDateTimeSelected: (Date) -> Unit
) {
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.getDefault())

    val calendar = remember { Calendar.getInstance() }

    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }

    if (showDatePicker.value) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                showDatePicker.value = false
                showTimePicker.value = true
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    if (showTimePicker.value) {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                onDateTimeSelected(calendar.time)
                showTimePicker.value = false
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    OutlinedTextField(
        value = dateFormat.format(calendar.time),

                onValueChange = { /* No-op */ },
        label = { Text("Pick Date & Time") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date",
                modifier = Modifier.clickable { showDatePicker.value = true }
            )
        }
    )
}

//@Composable
//fun DateTimePicker(
//    onDateTimeSelected: (Date) -> Unit
//) {
//    val context = LocalContext.current
//    val calendar = remember { Calendar.getInstance() }
//
//    val showDatePicker = remember { mutableStateOf(false) }
//    val showTimePicker = remember { mutableStateOf(false) }
//
//    if (showDatePicker.value) {
//        DatePickerDialog(
//            context,
//            { _, year, month, dayOfMonth ->
//                calendar.set(year, month, dayOfMonth)
//                showDatePicker.value = false
//                showTimePicker.value = true
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        ).show()
//    }
//
//    if (showTimePicker.value) {
//        TimePickerDialog(
//            context,
//            { _, hourOfDay, minute ->
//                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//                calendar.set(Calendar.MINUTE, minute)
//                onDateTimeSelected(calendar.time)
//                showTimePicker.value = false
//            },
//            calendar.get(Calendar.HOUR_OF_DAY),
//            calendar.get(Calendar.MINUTE),
//            true
//        ).show()
//    }
//
//    Button(onClick = { showDatePicker.value = true }) {
//        Text("Pick Date & Time")
//    }
//}
