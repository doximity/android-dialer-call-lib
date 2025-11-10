package com.doximity.callwithdoxdialer

import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doximity.callwithdoxdialerlib.DoxDialerCaller
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoxDialerSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DoxDialerSampleScreen()
                }
            }
        }
    }
}

@Composable
fun DoxDialerSampleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF0066CC),
            secondary = Color(0xFF00A0E3),
            tertiary = Color(0xFF5CB85C)
        ),
        content = content
    )
}

@Composable
fun DoxDialerSampleScreen() {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    val samplePhoneNumber = stringResource(R.string.sample_number)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // App Logo
        Image(
            painter = painterResource(id = R.drawable.doximity_dialer_icon),
            contentDescription = stringResource(R.string.icon_description),
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = stringResource(R.string.app_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.app_subtitle),
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Sample Phone Number Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.sample_number_label),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = samplePhoneNumber,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.sample_number_hint),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Phone Number Input
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { newValue ->
                // Format phone number as user types
                val formattedText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    formatPhoneNumber(newValue.text)
                } else {
                    newValue.text
                }
                // Update with formatted text and move cursor to end
                phoneNumber = TextFieldValue(
                    text = formattedText,
                    selection = TextRange(formattedText.length)
                )
            },
            label = { Text(stringResource(R.string.phone_input_label)) },
            placeholder = { Text(stringResource(R.string.phone_input_placeholder)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons
        // Prefill Dialer Button
        DialerActionButton(
            text = stringResource(R.string.button_prefill_dialer),
            backgroundColor = MaterialTheme.colorScheme.primary,
            onClick = {
                val numberToUse = if (phoneNumber.text.isNotBlank()) phoneNumber.text else samplePhoneNumber
                DoxDialerCaller.getInstance().dialPhoneNumber(context, numberToUse)
            },
            iconContent = {
                Icon(
                    imageVector = Icons.Filled.Dialpad,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Start Voice Call Button
        DialerActionButton(
            text = stringResource(R.string.button_start_voice_call),
            backgroundColor = Color(0xFF5CB85C),
            onClick = {
                val numberToUse = if (phoneNumber.text.isNotBlank()) phoneNumber.text else samplePhoneNumber
                DoxDialerCaller.getInstance().startVoiceCall(context, numberToUse)
            },
            iconContent = {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Start Video Call Button
        DialerActionButton(
            text = stringResource(R.string.button_start_video_call),
            backgroundColor = Color(0xFF9C27B0),
            onClick = {
                val numberToUse = if (phoneNumber.text.isNotBlank()) phoneNumber.text else samplePhoneNumber
                DoxDialerCaller.getInstance().startVideoCall(context, numberToUse)
            },
            iconContent = {
                Icon(
                    imageVector = Icons.Filled.Videocam,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Info Text
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF5F5F5)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.feature_descriptions_header),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.feature_descriptions_content),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun DialerActionButton(
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    iconContent: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        iconContent()
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Suppress("DEPRECATION")
private fun formatPhoneNumber(input: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        PhoneNumberUtils.formatNumber(input, Locale.getDefault().country) ?: input
    } else {
        PhoneNumberUtils.formatNumber(input) ?: input
    }
}
