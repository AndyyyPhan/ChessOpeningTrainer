package com.andyphan.chessopeningtrainer;

import org.hibernate.*;
import java.util.logging.Level;

public class DatabaseManager {
    private Session session;

    public DatabaseManager() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
    }
}
