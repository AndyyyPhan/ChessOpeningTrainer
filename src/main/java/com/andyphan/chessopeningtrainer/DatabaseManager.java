package com.andyphan.chessopeningtrainer;

import jakarta.persistence.PersistenceException;
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

    public boolean isPlayerExisting(String username) {
        Player existingPlayer = session.createQuery("FROM Player WHERE username = :username", Player.class)
                .setParameter("username", username)
                .uniqueResult();
        return existingPlayer != null;
    }

    public boolean isValidPlayer(String username, String password) {
        if (!isPlayerExisting(username)) return false;

        Player existingPlayer = session.createQuery("FROM Player WHERE username = :username", Player.class)
                .setParameter("username", username)
                .uniqueResult();
        return existingPlayer.getPassword().equals(password);
    }

    public boolean validPasswordLength(String password) {
        return password.length() >= 8;
    }

    public void addPlayer(String username, String password) {
        session.beginTransaction();
        try {
            Player player = new Player();
            player.setUsername(username);
            player.setPassword(password);
            session.persist(player);
            session.getTransaction().commit();
        }
        catch (PersistenceException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }
}
