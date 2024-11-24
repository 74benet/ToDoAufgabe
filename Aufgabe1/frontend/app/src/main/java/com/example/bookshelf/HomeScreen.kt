
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookshelf.ApiClient
import com.example.bookshelf.BookDetails
import com.example.bookshelf.BookSummary
import com.example.bookshelf.Review
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfApp(
    notificationsEnabled: MutableState<Boolean>,
    onToggleNotifications: (Boolean) -> Unit,
    onNotificationClick: () -> Unit,
    onSettingsClick: () -> Unit,
    drawerState: DrawerState
) {

    var selectedBookDetails by remember { mutableStateOf<BookDetails?>(null) }
    var selectedBookReviews by remember { mutableStateOf<List<Review>>(emptyList()) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    var books by remember { mutableStateOf<List<BookSummary>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val apiService = ApiClient.service
                books = apiService.getBookList().sortedBy{ it.id}
                if (books.isEmpty()) {
                    errorMessage = "Keine Bücher verfügbar."
                }
            } catch (e: Exception) {
                errorMessage = "Fehler beim Laden der Buchliste: ${e.message}"
            }
        }
    }

    ModalNavigationDrawer(
        drawerContent = {
            SettingsDrawerContent(
                notificationsEnabled = notificationsEnabled,
                onToggleNotifications = onToggleNotifications,
                onSimulateNotification = onNotificationClick
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Book Shelf", color = MaterialTheme.colorScheme.onPrimary) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = onSettingsClick,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    text = {
                        Text(
                            text = "Settings",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                StartPage(
                    bookList = books,
                    onBookClick = { book ->
                        coroutineScope.launch {
                            try {
                                selectedBookDetails = ApiClient.service.getBookDetails(book.id) // Lade Buchdetails
                                selectedBookReviews = ApiClient.service.getBookReviews(book.id) // Lade Reviews
                                sheetState.show()
                            } catch (e: Exception) {
                                errorMessage = "Fehler beim Laden der Buchdetails: ${e.message}"
                            }
                        }
                    }
                )
                if (selectedBookDetails != null) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            coroutineScope.launch { sheetState.hide() }
                            selectedBookDetails = null
                        },
                        sheetState = sheetState
                    ) {
                        BookDetailsBottomSheetContent(
                            book = selectedBookDetails!!,
                            reviews = selectedBookReviews
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDrawerContent(
    notificationsEnabled: MutableState<Boolean>,
    onToggleNotifications: (Boolean) -> Unit,
    onSimulateNotification: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                title = { Text("Settings") },
            )

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enable Notifications",
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Checkbox(

                    checked = notificationsEnabled.value,
                    onCheckedChange = { isChecked ->
                        notificationsEnabled.value = isChecked
                        onToggleNotifications(isChecked)
                    }
                )
            }

            Button(
                onClick = onSimulateNotification,
                enabled = notificationsEnabled.value,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Text("Simulate Notification")
            }
        }
    }
}



@Composable
fun StartPage(bookList: List<BookSummary>, onBookClick: (BookSummary) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(bookList) { book ->
            BookItem(book = book, onClick = { onBookClick(book) })
        }
    }
}

@Composable
fun BookItem(book: BookSummary, onClick: () -> Unit) {

    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val textColor = MaterialTheme.colorScheme.onSurface

    Surface(
        modifier = Modifier
            .aspectRatio(2f / 3f)
            .fillMaxWidth()
            .padding(4.dp)
            .graphicsLayer {
                shadowElevation = 8.dp.toPx()
                clip = true
            }
            .clickable(onClick = onClick),
        color = backgroundColor,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = book.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = textColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = book.author,
                fontSize = 12.sp,
                color = textColor.copy(alpha = 0.7f)
            )
        }
    }
}


@Composable
fun BookDetailsBottomSheetContent(book: BookDetails, reviews: List<Review>) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = book.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "${book.author}, ${book.year}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Reviews",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            reviews.forEach { review ->
                Text(
                    text = "${review.reviewer} (${review.stars}/5)",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = review.text,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
