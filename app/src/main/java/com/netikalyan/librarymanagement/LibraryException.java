/*
 * MIT License
 *
 * Copyright (c) 2018 netikalyan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.netikalyan.librarymanagement;

class LibraryException extends Exception {
    static class Constants {
        public static final String BOOK_ID_MISSING = "Book ID Missing";
        public static final String BOOK_TITLE_MISSING = "Title Missing";
        public static final String BOOK_AUTHOR_MISSING = "Author Name Missing";
        public static final String BOOK_PRICE_MISSING = "Price Missing";
        public static final String BOOK_AVAILABLE_COPIES_MISSING = "No. of Copies Missing";

        public static final String MEMBER_ID_MISSING = "Member ID Missing";
        public static final String MEMBER_NAME_MISSING = "Member Name Missing";
        public static final String MEMBER_INFO_MISSING = "Member Info Missing";

        public static final String TRANSACTION_ID_MISSING = "Transaction ID Missing";
        public static final String TRANSACTION_LOAN_DATE_MISSING = "Loan Date Missing";
        public static final String TRANSACTION_RETURN_DATE_MISSING = "Return Date Missing";
    }

    LibraryException(String message) {
        super(message);
    }
}
