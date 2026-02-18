package com.ElOuedUniv.maktaba.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
// ============ ADDED: Import for search icon ============
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
// ============ END ADDED ============
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
// ============ ADDED: Imports for search state management ============
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
// ============ END ADDED ============
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.presentation.viewmodel.BookViewModel

/**
 * Main screen displaying the list of books
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    viewModel: BookViewModel
) {
    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    // ============ ADDED: Collect total pages from ViewModel ============
    val totalPages by viewModel.totalPages.collectAsState()
    // ============ END ADDED ============
    // ============ ADDED: Search query state ============
    var searchQuery by remember { mutableStateOf("") }
    // ============ END ADDED ============

    // ============ ADDED: Filter books based on search query ============
    val filteredBooks = remember(books, searchQuery) {
        if (searchQuery.isEmpty()) {
            books
        } else {
            books.filter { it.title.contains(searchQuery, ignoreCase = true) }
        }
    }
    // ============ END ADDED ============

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Maktaba - My Library") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

        }
    ) { paddingValues ->
        // ============ ADDED: Changed Box to Column to add search field ============
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // ============ ADDED: Book Counter ============
            Text(
                text = "Total Books: ${books.size}",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            // ============ END ADDED ============

            // ============ ADDED: Total Pages Counter ============
            Text(
                text = "Total Pages: $totalPages",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            // ============ END ADDED ============

            // ============ ADDED: Search TextField ============
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search by book title...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true
            )
            // ============ END ADDED ============

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    // ============ MODIFIED: Use filteredBooks instead of books ============
                    if (filteredBooks.isEmpty()) {
                        EmptyBooksMessage(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        BookList(
                            books = filteredBooks,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    // ============ END MODIFIED ============
                }
            }
        }
        // ============ END ADDED ============
    }
}

/**
 * Composable for displaying a list of books
 */
@Composable
fun BookList(
    books: List<Book>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books) { book ->
            BookItem(book = book)
        }
    }
}

/**
 * Composable for displaying a single book item
 */
@Composable
fun BookItem(book: Book) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // ============ ADDED: Author field ============
            Text(
                text = "Author: ${if (book.author.isEmpty()) "Not set" else book.author}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            // ============ END ADDED ============

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "ISBN:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (book.isbn.isEmpty()) "Not set" else book.isbn,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Pages:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (book.nbPages == 0) "Not set" else "${book.nbPages}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

/**
 * Composable for displaying empty state message
 */
@Composable
fun EmptyBooksMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📚",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No books in your library",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Complete the TODO exercises in BookRepository.kt",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}