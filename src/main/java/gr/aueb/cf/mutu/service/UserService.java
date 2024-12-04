package gr.aueb.cf.mutu.service;

import gr.aueb.cf.mutu.dao.IMessageDao;
import gr.aueb.cf.mutu.dao.IUserDao;

public class UserService {
    static IUserDao userDao;

    static {
        String config = System.getProperty("config");
        switch (config) {
            case "dev": {
                userDao = new gr.aueb.cf.mutu.models_dev.UserDao();
            } break;

            case "prod": {
                userDao = new gr.aueb.cf.mutu.models_prod.UserDao();
            } break;
        }
    }

    public static IUserDao getImpl() {
        return userDao;
    }
}
