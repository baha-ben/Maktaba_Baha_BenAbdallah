package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {

    private val booksList = mutableListOf(
        Book(
            isbn = "978-0-13-235088-4",
            title = "Clean Code",
            nbPages = 431,
            author = "Robert C. Martin",
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780132350884-L.jpg"
        ),
        Book(
            isbn = "978-0-13-595705-9",
            title = "The Pragmatic Programmer",
            nbPages = 320,
            author = "Andrew Hunt & David Thomas",
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780135957059-L.jpg"
        ),
        Book(
            isbn = "978-0-201-63361-0",
            title = "Design Patterns",
            nbPages = 395,
            author = "Erich Gamma, Richard Helm, Ralph Johnson",
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780201633610-L.jpg"
        ),
        Book(
            isbn = "978-0-13-475759-9",
            title = "Refactoring",
            nbPages = 448,
            author = "Martin Fowler",
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780134757599-L.jpg"
        ),
        Book(
            isbn = "9780596007126",
            title = "Head First Design Patterns",
            nbPages = 672,
            author = "Eric Freeman & Elisabeth Robson",
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780596007126-L.jpg"
        ),
        Book(
            isbn = "978-0-262-04630-5",
            title = "Introduction to Algorithms",
            nbPages = 1312,
            author = "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest",
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780262046305-L.jpg"
        ),
        Book(
            isbn = "978-0-13-434001-2",
            title = "How to Solve It by Computer",
            nbPages = 442,
            author = "R. G. Dromey",
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780134340012-L.jpg"
        ),
        Book(
            isbn = "9780321842688",
            title = "Hacker's Delight",
            nbPages = 512,
            author = "Henry S. Warren, Jr",
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780321842688-L.jpg"
        ),
        Book(
            isbn = "0130224189",
            title = "Algorithms + Data Structures = Programs",
            nbPages = 366,
            author = "Niklaus Wirth",
            imageUrl = "https://covers.openlibrary.org/b/isbn/0130224189-L.jpg"
        ),
        Book(
            isbn = "0596000278",
            title = "Programming Perl",
            nbPages = 1184,
            author = "Larry Wall, Randal L. Schwartz, Tom Christiansen, Jon Orwant",
            imageUrl = "https://covers.openlibrary.org/b/isbn/0596000278-L.jpg"
        )
    )

    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(booksList.toList())
    }
    
    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000) // Simulate delay
        emitAll(booksFlow)
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }

    override fun addBook(book: Book) {
        booksList.add(book)
        booksFlow.tryEmit(booksList.toList())
    }
}
