package com.example.myapplication.utils.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly

@Composable
fun CustomizedTextField(
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal
    ),
    placeholderStyle: TextStyle = textStyle.copy(color = Color.Gray),
    value: String,
    onValueChange: (String) -> Unit,
    maxLines: Int = 1,
    maxLength: Int = 128,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    label: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (label != null) {
            Text(
                text = label,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(4.dp),
                    color = Color.Gray
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(16.dp),
            value = value,
            onValueChange = {
                if (it.length > maxLength || it == value || (value.isBlank() && it.isBlank()) || (it.length >= 2 && it.last()
                        .isWhitespace() && it[it.length - 2].isWhitespace())
                ) {
                    return@BasicTextField
                }
                if (keyboardOptions.keyboardType == KeyboardType.Number) {
                    if (it.isDigitsOnly()) {
                        onValueChange(it)
                    }
                } else {
                    onValueChange(it)
                }
            },
            maxLines = maxLines,
            singleLine = singleLine,
            readOnly = readOnly,
            enabled = enabled,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty() && placeholder != null) {
                            Text(text = placeholder, style = placeholderStyle)
                        }
                        innerTextField()
                    }
                }
            })
    }
}