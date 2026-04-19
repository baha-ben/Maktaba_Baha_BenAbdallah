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
            author = "Robert C. Martin"
        ),
        Book(
            isbn = "978-0-13-595705-9",
            title = "The Pragmatic Programmer",
            nbPages = 320,
            author = "Andrew Hunt & David Thomas"
        ),
        Book(
            isbn = "978-0-201-63361-0",
            title = "Design Patterns",
            nbPages = 395,
            author = "Erich Gamma, Richard Helm, Ralph Johnson"
        ),
        Book(
            isbn = "978-0-13-475759-9",
            title = "Refactoring",
            nbPages = 448,
            author = "Martin Fowler"
        ),
        Book(
            isbn = "978-1-492-07799-2",
            title = "Head First Design Patterns",
            nbPages = 672,
            author = "Eric Freeman & Elisabeth Robson"
        ),
        Book(
            isbn = "978-0-262-04630-5",
            title = "Introduction to Algorithms",
            nbPages = 1312,
            author = "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest"
        ),
        Book(
            isbn = "978-0-13-434001-2",
            title = "How to Solve It by Computer",
            nbPages = 442,
            author = "R. G. Dromey"
        ),
        Book(
            isbn = "978-0-13-308501-3",
            title = "Hacker's Delight",
            nbPages = 512,
            author = "Henry S. Warren, Jr"
        ),
        Book(
            isbn = "978-0-13-022418-7",
            title = "Algorithms + Data Structures = Programs",
            nbPages = 366,
            author = "Niklaus Wirth"
        ),
        Book(
            isbn = "978-0-596-00492-7",
            title = "Programming Perl",
            nbPages = 1184,
            author = "Larry Wall, Randal L. Schwartz, Tom Christiansen, Jon Orwant"
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
