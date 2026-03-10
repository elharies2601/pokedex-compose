package id.elharies.pokedex.ui.profile

import android.R.attr.name
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.elharies.pokedex.R
import id.elharies.pokedex.component.CirclePokemonPlaceholder
import id.elharies.pokedex.component.LoadingDialog
import id.elharies.pokedex.domain.model.User
import id.elharies.pokedex.ui.theme.Black
import id.elharies.pokedex.ui.theme.BlackBackground
import id.elharies.pokedex.ui.theme.LightGreen
import id.elharies.pokedex.ui.theme.Red
import id.elharies.pokedex.ui.theme.WhiteBone
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect("getProfile") {
        viewModel.onAction(ProfileIntent.GetProfile)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                is ProfileEvent.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                ProfileEvent.SuccessLogout -> onLogout()
            }
        }
    }

    ProfileContent(uiState = uiState, onAction = viewModel::onAction)
}

@Composable
private fun ProfileContent(uiState: ProfileUiState = ProfileUiState(), onAction: (ProfileIntent) -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize().background(WhiteBone)
    ) {
        Text(
            text = stringResource(R.string.title_pokedex),
            fontSize = 44.sp,
            fontWeight = FontWeight.Black,
            color = BlackBackground,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(150.dp))
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color.White
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                ProfileHeader(user = uiState.user)
                Spacer(modifier = Modifier.height(100.dp))
                OutlinedButton(
                    onClick = { onAction(ProfileIntent.Logout) },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(2.dp, Red)
                ) {
                    Text("Logout", color = Red)
                }
            }
        }
    }

    LoadingDialog(isShow = uiState.isLoading)
}

@Composable
private fun ProfileHeader(
    modifier: Modifier = Modifier,
    user: User = User()
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        CirclePokemonPlaceholder(modifier = Modifier, size = 80.dp)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = user.name,
                fontSize = 44.sp,
                fontWeight = FontWeight.Black,
                color = Black
            )
            Text(
                text = user.email,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProfileHeader() {
    val user = User(name = "Ashh", email = "assh@asssh.com")
    ProfileHeader(user = user)
}

@Preview
@Composable
private fun PreviewProfileContent() {
    ProfileContent()
}