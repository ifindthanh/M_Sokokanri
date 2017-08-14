package vn.com.nsmv.dao;

import java.util.List;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Transaction;

public interface TransactionDAO {
    public Long addTransaction(Transaction transaction) throws SokokanriException;

    public List<Transaction> listAllTransactions(Long userId) throws SokokanriException;
}
