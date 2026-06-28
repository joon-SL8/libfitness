package com.skjline.fitness.presentation.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.presentation.shared.style.Dimens

@Composable
fun LyncProfileTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    label: String,
    unit: String,
    content: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Unspecified,
        autoCorrectEnabled = false,
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Unspecified,
        platformImeOptions = null,
        showKeyboardOnFocus = null,
        hintLocales = null
    ),
    changeListener: (String) -> Unit,
) {
    val data = remember { mutableStateOf(content) }
    TextField(
        modifier = modifier,
        value = data.value,
        onValueChange = { value: String ->
            data.value = value
            changeListener(value)
        },
        label = { Text(text = label) },
        singleLine = true,
        shape = RoundedCornerShape(Dimens.RoundedCorner.Normal.asDP()),
        visualTransformation = {
            TransformedText(
                text = AnnotatedString("${it.text} ${unit.ifEmpty { EMPTY }}"),
                offsetMapping = object : OffsetMapping {
                    override fun originalToTransformed(offset: Int): Int = offset
                    override fun transformedToOriginal(offset: Int): Int {
                        return if (offset > data.value.length) {
                            data.value.length
                        } else {
                            offset
                        }
                    }

                }
            )
        },
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = keyboardOptions,
    )
}
