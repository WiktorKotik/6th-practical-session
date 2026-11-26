package com.example.composetutorial

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

/*
 * Задание 2.2: все изученные на уроках 6-10 элементы
 * размещены на одном экране.
 */
@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "ПЗ.6: Jetpack Compose",
            style = MaterialTheme.typography.headlineSmall
        )
        Lesson6CounterSection()
        Lesson7CheckboxSection()
        Lesson7TextFieldsSection()
        Lesson8RecompositionSection()
        Lesson9RememberSection()
        HorizontalDivider()
        Text(
            text = "Уроки 6-10: state, checkbox и textfield, " +
                "recomposition, remember, remember + mutableStateOf",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

// Урок 6: состояние (state) и урок 10: remember + mutableStateOf
@Composable
fun Lesson6CounterSection() {
    var count by remember { mutableStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Урок 6: счётчик на состоянии", style = MaterialTheme.typography.titleMedium)
        Text("Значение счётчика: $count", style = MaterialTheme.typography.bodyLarge)
        Row {
            Button(onClick = { count++ }) { Text("+1") }
            OutlinedButton(
                onClick = { count = 0 },
                modifier = Modifier.padding(start = 8.dp)
            ) { Text("Сброс") }
        }
    }
}

// Урок 7: Checkbox
@Composable
fun Lesson7CheckboxSection() {
    var checked by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
        Text(
            text = if (checked) "Задача выполнена" else "Задача не выполнена",
            textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
        )
    }
}

// Урок 7: TextField и обработка ввода
@Composable
fun Lesson7TextFieldsSection() {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Урок 7: TextField", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Логин") },
            isError = loginError,
            supportingText = {
                if (loginError) Text("Логин не может быть пустым")
            }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            if (login.isBlank() || password.isBlank()) {
                loginError = login.isBlank()
                message = "Заполните оба поля"
            } else {
                loginError = false
                message = "Привет, $login!"
            }
        }) { Text("Войти") }
        if (message.isNotEmpty()) {
            Text(message, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// Урок 8: рекомпозиция
@Composable
fun Lesson8RecompositionSection() {
    var clicks by remember { mutableStateOf(0) }
    // Лог в Logcat показывает, что функция выполняется заново при каждом изменении
    Log.d("Recomposition", "Экран перерисовался, кликов: $clicks")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Урок 8: рекомпозиция", style = MaterialTheme.typography.titleMedium)
        Text("Кликов по кнопке: $clicks")
        Button(onClick = { clicks++ }) { Text("Ещё клик") }
        Text(
            text = "При каждом клике в Logcat появляется новое сообщение: " +
                "функция MainScreen выполнилась заново",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

// Урок 9: зачем нужен remember
@Composable
fun Lesson9RememberSection() {
    var withoutRemember = mutableStateOf(0)
    var withRemember by remember { mutableStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Урок 9: remember", style = MaterialTheme.typography.titleMedium)
        Row {
            OutlinedButton(onClick = { withoutRemember.value++ }) { Text("+1 без remember") }
            OutlinedButton(
                onClick = { withRemember++ },
                modifier = Modifier.padding(start = 8.dp)
            ) { Text("+1 с remember") }
        }
        Text(
            text = "Без remember: ${withoutRemember.value} (сбрасывается при рекомпозиции)",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "С remember: $withRemember (значение сохраняется)",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
