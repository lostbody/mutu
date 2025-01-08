package gr.aueb.cf.mutu.service;
import gr.aueb.cf.mutu.dao.IUserActionDao;

public class UserActionService {
    static IUserActionDao userActionDao;

    static {
        String config = System.getenv("MUTU_CONFIG");
        switch (config) {
            case "dev": {
                userActionDao = new gr.aueb.cf.mutu.models_dev.UserActionDao();
            } break;

            case "prod": {
                userActionDao = new gr.aueb.cf.mutu.models_prod.UserActionDao();
            } break;
        }
    }

    public static IUserActionDao getImpl() { return userActionDao; }
}
