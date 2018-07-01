package com.example.android.bookstoreapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the BookStoreApp.
 */
public class BookContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.bookstoreapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BOOKS = "books";

    /**
     * Empty constructor
     */
    private BookContract() {
    }

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static abstract class BookEntry implements BaseColumns {

        /**
         * The content URI to access the book data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        /**
         * Name of database table for books
         */
        public static final String TABLE_NAME = "books";


        /**
         * Unique ID number for the book (only for use in the database table).
         * Type: INTEGER
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Name of the book.
         * Type: TEXT
         */
        public static final String COLUMN_BOOK_NAME = "name";

        /**
         * Author of the book.
         * Type: TEXT
         */
        public static final String COLUMN_AUTHOR_NAME = "author";

        /**
         * Price of the book.
         * Type: REAL
         */
        public static final String COLUMN_BOOK_PRICE = "price";

        /**
         * Quantity of the book.
         * Type: INTEGER
         */
        public static final String COLUMN_BOOK_QUANTITY = "quantity";

        /**
         * Supplier of the book.
         * Type: TEXT
         */
        public static final String COLUMN_BOOK_SUPPLIER = "supplier";

        /**
         * Supplier's phone number.
         * Type: TEXT
         */
        public static final String COLUMN_BOOK_SUPPLIER_PHONE_NUMBER = "phone_number";

        /**
         * The MIME type for a list of books.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /**
         * The MIME type for a single book.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;
    }
}
