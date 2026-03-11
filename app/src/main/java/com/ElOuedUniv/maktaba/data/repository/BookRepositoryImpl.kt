package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

class BookRepositoryImpl : BookRepository {

    /**
     * TODO for Students (TP1 - Exercise 1):
     * Complete the book information for each book in the list below.
     * Add the following information for each book:     * - isbn: Use a valid ISBN-13 format (e.g., "978-3-16-148410-0")     * - nbPages: Add the actual number of pages     *     * Example:     * Book(     *     isbn = "978-0-13-468599-1",     *     title = "Clean Code",     *     nbPages = 464     * )     */    private val booksList = listOf(
        Book(isbn = "978-0-13-235088-4", title = "Clean Code", nbPages = 431 , author = "Robert C. Martin"),
        Book(isbn = "978-0-13-595705-9", title = "The Pragmatic Programmer", nbPages = 320, author = "Andrew Hunt & David Thomas"),
        Book(isbn = "978-0-201-63361-0", title = "Design Patterns", nbPages = 395, author = "Erich Gamma, Richard Helm, Ralph Johnson"),
        Book(isbn = "978-0-13-475759-9", title = "Refactoring", nbPages = 448, author = "Martin Fowler"),
        Book(isbn = "978-1-492-07799-2", title = "Head First Design Patterns", nbPages = 672, author = "Eric Freeman & Elisabeth Robson"),
        Book(isbn = "978-0-262-04630-5", title = "Introduction to Algorithms", nbPages = 1312, author = "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest"),
        Book(isbn = "978-0-13-434001-2", title = "How to Solve It by Computer", nbPages = 442, author = "R. G. Dromey"),
        Book(isbn = "978-0-13-308501-3", title = "Hacker's Delight", nbPages = 512, author = "Henry S. Warren, Jr"),
        Book(isbn = "978-0-13-022418-7", title = "Algorithms + Data Structures = Programs", nbPages = 366, author = "Niklaus Wirth"),
        Book(isbn = "978-0-596-00492-7", title = "Programming Perl", nbPages = 1184, author = "Larry Wall, Randal L. Schwartz, Tom Christiansen, Jon Orwant"),
    )

    /**
     * TODO for Students (TP1 - Exercise 2):
     * Add 5 more books to the list above.
     * Choose books related to Computer Science, Programming, or any topic you like.     * Remember to include complete information (ISBN, title, nbPages).     *     * Tip: You can find ISBN numbers for books on:     * - Google Books     * - Amazon     * - GoodReads     */
    /**     * Get all books from the repository     * @return List of all books
     */
    override fun getAllBooks(): List<Book> {
        return booksList
    }

    /**
     * Get a book by ISBN     * @param isbn The ISBN of the book to find
     * @return The book if found, null otherwise
     */
    override fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }

    /**
     * Filter books by title     */    fun filterBooksByTitle(query: String): List<Book> {
        if (query.isEmpty()) {
            return booksList
        }
        return booksList.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

}


