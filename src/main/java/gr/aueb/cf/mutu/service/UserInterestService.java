package gr.aueb.cf.mutu.service;
import gr.aueb.cf.mutu.dao.IUserInterestDao;

public class UserInterestService {
    static IUserInterestDao userInterestDao;

    static {
        String config = System.getProperty("config");
        switch (config) {
            case "dev": {
                userInterestDao = new gr.aueb.cf.mutu.models_dev.UserInterestDao();
            } break;

            case "prod": {
                userInterestDao = new gr.aueb.cf.mutu.models_prod.UserInterestDao();
            } break;
        }
    }

    public static IUserInterestDao getImpl() { return userInterestDao; }
}
