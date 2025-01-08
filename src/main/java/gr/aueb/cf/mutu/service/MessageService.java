package gr.aueb.cf.mutu.service;

import gr.aueb.cf.mutu.dao.IMessageDao;

public class MessageService {
    static IMessageDao messageDao;

    static {
        String config = System.getProperty("config");
        switch (config) {
            case "dev": {
                messageDao = new gr.aueb.cf.mutu.models_dev.MessageDao();
            } break;

            case "prod": {
                messageDao = new gr.aueb.cf.mutu.models_prod.MessageDao();
            } break;
        }
    }

    public static IMessageDao getImpl() {
        return messageDao;
    }
}
