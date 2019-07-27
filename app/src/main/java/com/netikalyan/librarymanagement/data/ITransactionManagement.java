package com.netikalyan.librarymanagement.data;

public interface ITransactionManagement {
    void loanBookToMember(TransactionEntity transaction);

    TransactionEntity search(int transactionID);

    void returnBookToLibrary(TransactionEntity transaction);

    void deleteTransaction(TransactionEntity transaction);
}
