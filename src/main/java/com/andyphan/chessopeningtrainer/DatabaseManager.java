package com.andyphan.chessopeningtrainer;

import jakarta.persistence.PersistenceException;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;
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
