package io.ylab.intensive.lesson05.messagefilter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeoutException;


public class MessageFilterApp {
    public static void main(String[] args) throws IOException, SQLException, TimeoutException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        Db db = applicationContext.getBean(Db.class);
        db.primaryWorkWithDb();
        MessageHandler messageHandler = applicationContext.getBean(MessageHandler.class);
        messageHandler.handler();
    }
}





