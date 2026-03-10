package id.elharies.pokedex.ui.login

import android.R.attr.name
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.elharies.pokedex.R
import id.elharies.pokedex.component.HeaderGeneralSection
import id.elharies.pokedex.component.LoadingDialog
import id.elharies.pokedex.icons.ImagePokeBallGrey
import id.elharies.pokedex.ui.theme.Grey
import id.elharies.pokedex.ui.theme.LightGreen
import id.elharies.pokedex.util.isValidEmail
import kotlinx.coroutines.flow.Flow

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavToHome: () -> Unit = {},
    onNavToRegister: () -> Unit = {}
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(LoginIntent.CheckIsLoggedIn)
    }

    LoginContent(uiState = uiState, event = viewModel.event, onLogin = { email, password ->
        viewModel.onAction(LoginIntent.Login(email, password))
    }, onNavToRegister = {
        onNavToRegister()
    }, onNavToHome = onNavToHome)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginContent(
    uiState: LoginUiState = LoginUiState(),
    event: Flow<LoginEvent>? = null,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onNavToRegister: () -> Unit = {},
    onNavToHome: () -> Unit = {},
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = Unit) {
        event?.collect {
            when (it) {
                is LoginEvent.NavigateToHome -> onNavToHome()
                is LoginEvent.ShowToast -> {
                    snackbarHostState.showSnackbar(it.message)
                }

                else -> {}
            }
        }
    }

    if (uiState.alreadyLogin) {
        onNavToHome()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGreen,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
        Box(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(), start = it.calculateStartPadding(
                    LayoutDirection.Rtl
                ), end = it.calculateEndPadding(LayoutDirection.Rtl)
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.align(Alignment.Center),
            ) {
                Icon(
                    painterResource(R.drawable.ic_pokeball_grey),
                    contentDescription = "ic",
                    tint = Grey.copy(alpha = 0.1f)
                )
                Spacer(modifier = Modifier.height(150.dp))
            }
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomCenter)
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                HeaderGeneralSection()
                BodyLogin(
                    onLogin = onLogin,
                    onRegister = onNavToRegister,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
    LoadingDialog(isShow = uiState.isLoading)
}

@Composable
private fun HeaderLogin(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.title_pokedex),
            fontSize = 44.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
        Text(
            text = stringResource(R.string.title_login),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Icon(
            painterResource(R.drawable.ic_pokeball_grey),
            contentDescription = "image poke ball",
            tint = Color.Transparent
        )
    }
}

@Composable
private fun BodyLogin(
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onRegister: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val areAllFilled by remember {
        derivedStateOf {
            email.isNotEmpty() &&
                    email.isValidEmail() &&
                    password.isNotEmpty()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(), color = Color.White,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text(stringResource(R.string.email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text(stringResource(R.string.password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onLogin(email, password) },
                colors = ButtonDefaults.buttonColors().copy(containerColor = LightGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = areAllFilled
            ) {
                Text(stringResource(R.string.title_login))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                stringResource(R.string.atau), fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { onRegister() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text(stringResource(R.string.title_register))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBodyLogin() {
    BodyLogin()
}

@Preview
@Composable
private fun PreviewHeaderLogin() {
    HeaderLogin()
}

@Preview
@Composable
private fun PreviewLoginContent() {
    LoginContent()
}