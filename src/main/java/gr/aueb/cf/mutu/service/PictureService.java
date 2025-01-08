package gr.aueb.cf.mutu.service;

import gr.aueb.cf.mutu.dao.IPictureDao;
import gr.aueb.cf.mutu.dao.IUserActionDao;

public class PictureService {
    static IPictureDao pictureDao;

    static {
        String config = System.getenv("MUTU_CONFIG");
        switch (config) {
            case "dev": {
                pictureDao = new gr.aueb.cf.mutu.models_dev.PictureDao();
            } break;

            case "prod": {
                pictureDao = new gr.aueb.cf.mutu.models_prod.PictureDao();
            } break;
        }
    }

    public static IPictureDao getImpl() { return pictureDao; }
}
