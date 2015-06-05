package com.stonegate.invoice.autoinvoice.dao;

import com.stonegate.invoice.autoinvoice.bean.User;

/**
 * @author chao.zhu created on 15/6/5 下午11:31
 * @version 1.0
 */
public interface UserDao {
    User getUserByUserId(String userId);
}
