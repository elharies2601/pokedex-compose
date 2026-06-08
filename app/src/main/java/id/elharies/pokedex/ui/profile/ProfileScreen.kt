package id.elharies.pokedex.ui.profile

import android.R.attr.name
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.elharies.pokedex.R
import id.elharies.pokedex.component.LoadingDialog
import id.elharies.pokedex.domain.model.User
import id.elharies.pokedex.ui.theme.Black
import id.elharies.pokedex.ui.theme.Grey
import id.elharies.pokedex.ui.theme.LightGreen
import id.elharies.pokedex.ui.theme.LighterGrey
import id.elharies.pokedex.ui.theme.Red
import id.elharies.pokedex.ui.theme.WhiteBone
import id.elutility.core.ext.getCurrentTime
import id.elutility.core.ext.toString
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
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(LightGreen)
            ) {
                Icon(
                    painterResource(R.drawable.ic_pokeball_grey),
                    contentDescription = "ic",
                    tint = Grey.copy(alpha = 0.05f),
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp)
                )
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = WhiteBone
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    ProfileHeader(user = uiState.user)
                    Spacer(modifier = Modifier.height(24.dp))
                    ProfileInfoCard(user = uiState.user)
                    Spacer(modifier = Modifier.height(16.dp))
                    QuickActionsRow()
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { onAction(ProfileIntent.Logout) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Red,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_logout),
                            contentDescription = "Logout",
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 4.dp)
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            stringResource(R.string.logout),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 140.dp)
        ) {
            ProfileAvatar(userName = uiState.user.name)
        }
    }

    LoadingDialog(isShow = uiState.isLoading)
}

@Composable
private fun ProfileAvatar(
    modifier: Modifier = Modifier,
    userName: String = ""
) {
    Card(
        modifier = modifier.size(96.dp),
        shape = androidx.compose.foundation.shape.CircleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = LightGreen
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userName.take(2).uppercase(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    modifier: Modifier = Modifier,
    user: User = User()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 60.dp)
    ) {
        Text(
            text = user.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = user.email,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Grey
        )
    }
}

@Composable
private fun ProfileInfoCard(
    modifier: Modifier = Modifier,
    user: User = User()
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            InfoRow(
                icon = R.drawable.ic_person,
                label = stringResource(R.string.nama),
                value = user.name
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = LighterGrey
            )
            InfoRow(
                icon = R.drawable.ic_email,
                label = stringResource(R.string.email),
                value = user.email
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = LighterGrey
            )
            InfoRow(
                icon = R.drawable.ic_calendar,
                label = stringResource(R.string.member_sejak),
                value = getCurrentTime().toString("MMM yyyy")
            )
        }
    }
}

@Composable
private fun InfoRow(
    icon: Int,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = LightGreen,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Grey
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Black,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun QuickActionsRow(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuickActionCard(
            icon = R.drawable.ic_edit,
            label = "Edit Profile",
            modifier = Modifier.weight(1f)
        ) {
            Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
        }
        QuickActionCard(
            icon = R.drawable.ic_settings,
            label = "Settings",
            modifier = Modifier.weight(1f)
        )  {
            Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
        }
        QuickActionCard(
            icon = R.drawable.ic_help,
            label = "Help",
            modifier = Modifier.weight(1f)
        )  {
            Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = label,
                tint = LightGreen,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Black,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileAvatar() {
    ProfileAvatar(userName = "Ash Ketchum")
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileInfoCard() {
    val user = User(name = "Ash Ketchum", email = "ash@pokemon.com")
    ProfileInfoCard(user = user)
}

@Preview(showBackground = true)
@Composable
private fun PreviewQuickActionsRow() {
    QuickActionsRow()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewProfileContent() {
    val user = User(name = "Ash Ketchum", email = "ash@pokemon.com")
    ProfileContent(uiState = ProfileUiState(user = user))
}