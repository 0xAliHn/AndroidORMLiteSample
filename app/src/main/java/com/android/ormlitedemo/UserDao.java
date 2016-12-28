package com.android.ormlitedemo;

import com.android.ormlitedemo.bean.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDao {

    public static UserDao mUserDaoInstance;

    private Dao<User, Integer> mUserDao;

    public UserDao() {
        try {
            mUserDao = DatabaseHelper.getInstance().getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserDao getInstance() {
        if (mUserDaoInstance == null) {
            mUserDaoInstance = new UserDao();
        }
        return mUserDaoInstance;
    }

    public void insertUser(User user) {
        try {
            mUserDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


/*    public void insertUsers(List<User> users) {
        try {
            mUserDao.create(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/


    public List<User> queryAllUser() {
        List<User> users = new ArrayList<>();
        try {
            users = mUserDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public User queryUserById(int id) {
        User user = null;
        try {
            user = mUserDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public void deleteUserById(int id) {
        try {
            mUserDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteUserByIds(List<Integer> ids) {
        try {
            mUserDao.deleteIds(ids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteAllUser() {
        try {
            mUserDao.deleteBuilder().delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateUser(User user) {
        try {
            mUserDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateUserById(User user, int id) {
        try {
            mUserDao.updateId(user, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> queryBy() throws SQLException {
        QueryBuilder<User, Integer> queryBuilder = mUserDao
                .queryBuilder();
        Where<User, Integer> where = queryBuilder.where();
        where.eq("user_id", 1);
        where.and();
        where.eq("name", "xxx");

        mUserDao.queryBuilder().
                where().
                eq("user_id", 1).and().
                eq("name", "xxx");
        return queryBuilder.query();
    }
}
