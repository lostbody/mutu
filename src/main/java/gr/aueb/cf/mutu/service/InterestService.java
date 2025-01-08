package gr.aueb.cf.mutu.service;

import gr.aueb.cf.mutu.dao.IInterestDao;
import gr.aueb.cf.mutu.dao.IPictureDao;

public class InterestService {
    static IInterestDao interestDao;

    static {
        String config = System.getenv("MUTU_CONFIG");
        switch (config) {
            case "dev": {
                interestDao = new gr.aueb.cf.mutu.models_dev.InterestDao();
            } break;

            case "prod": {
                interestDao = new gr.aueb.cf.mutu.models_prod.InterestDao();
            } break;
        }
    }

    public static IInterestDao getImpl() { return interestDao; }
}
