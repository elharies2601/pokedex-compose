package id.elharies.pokedex.ui.register

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.elharies.pokedex.R
import id.elharies.pokedex.component.HeaderGeneralSection
import id.elharies.pokedex.component.LoadingDialog
import id.elharies.pokedex.ui.theme.Grey
import id.elharies.pokedex.ui.theme.LightGreen
import id.elharies.pokedex.util.isValidEmail
import kotlinx.coroutines.flow.Flow

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RegisterContent(uiState = state, event = viewModel.event, onBack = onBack, onRegister = { nama, email, password ->
        viewModel.onAction(RegisterIntent.Register(nama, email, password))
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterContent(
    uiState: RegisterUiState = RegisterUiState(),
    event: Flow<RegisterEvent>? = null,
    onBack: () -> Unit = {},
    onRegister: (String, String, String) -> Unit = {_,_,_ ->}
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        event?.collect {
            when(it) {
                is RegisterEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(it.message)
                }
                is RegisterEvent.Success -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    val result = snackbarHostState.showSnackbar(it.message, actionLabel = "OK", false, duration = SnackbarDuration.Short)
                    when(result) {
                        SnackbarResult.Dismissed -> {}
                        SnackbarResult.ActionPerformed -> {
                            onBack()
                        }
                    }
                }
                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGreen,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, topBar = {
            TopAppBar(title = {}, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(painterResource(R.drawable.ic_back), contentDescription = "icon back", tint = Color.White)
                }
            }, colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = Color.Transparent))
        },) {
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
                HeaderGeneralSection(title = stringResource(R.string.title_register))
                BodyRegister(
                    onRegister = onRegister,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
    LoadingDialog(isShow = uiState.isLoading)
}

@Composable
private fun BodyRegister(modifier: Modifier = Modifier, onRegister: (String, String, String) -> Unit = {_,_,_ ->}) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val areAllFilled by remember {
        derivedStateOf {
            email.isNotEmpty() &&
                    email.isValidEmail() &&
                    password.isNotEmpty() && nama.isNotEmpty()
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
                nama,
                onValueChange = { nama = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text(stringResource(R.string.label_nama)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            Spacer(modifier = Modifier.height(16.dp))
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
                onClick = { onRegister(nama, email, password) },
                colors = ButtonDefaults.buttonColors().copy(containerColor = LightGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = areAllFilled
            ) {
                Text(stringResource(R.string.title_register))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBodyRegister() {
    BodyRegister()
}

@Preview
@Composable
private fun PreviewRegisterContent() {
    RegisterContent()
}