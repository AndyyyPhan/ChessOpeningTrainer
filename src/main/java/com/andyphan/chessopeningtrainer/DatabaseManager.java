package com.andyphan.chessopeningtrainer;

import jakarta.persistence.PersistenceException;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
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

    public boolean isUserExisting(String username) {
        User existingUser = session.createQuery("FROM User WHERE username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();
        return existingUser != null;
    }

    public boolean isValidUser(String username, String password) {
        if (!isUserExisting(username)) return false;

        User existingUser = session.createQuery("FROM User WHERE username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();
        return existingUser.getPassword().equals(password);
    }

    public boolean validPasswordLength(String password) {
        return password.length() >= 8;
    }

    public void addUser(String username, String password) {
        session.beginTransaction();
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            session.persist(user);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
        }
    }

    public User getUser(String username) {
        return session.createQuery("FROM User WHERE username =:username", User.class)
                .setParameter("username", username)
                .uniqueResult();
    }

    public List<ChessOpening> getOpeningsInPractice() {
        List<ChessOpening> chessOpenings = Collections.emptyList();
        session.beginTransaction();
        try {
            Query<ChessOpening> query = session.createQuery("FROM ChessOpening", ChessOpening.class);
            chessOpenings = query.getResultList();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
        }
        return chessOpenings;
    }

    public void addChessOpening(ChessOpening chessOpening) throws IOException {
        if (chessOpening.getFen().isBlank()) chessOpening.setFen(ChessOpeningsParser.getNextCreatedOpeningFen());
        User currentUser = UserManager.getCurrentUser();
        chessOpening.setUser(currentUser);
        session.beginTransaction();
        try {
            session.persist(chessOpening);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
        }
    }

    public boolean isChessOpeningExisting(ChessOpening chessOpening) {
        ChessOpening existingChessOpening = session.createQuery("FROM ChessOpening WHERE fen = :fen", ChessOpening.class)
                .setParameter("fen", chessOpening.getFen())
                .uniqueResult();
        return existingChessOpening != null;
    }

    public void deleteChessOpening(ChessOpening chessOpening) {
        session.beginTransaction();
        try {
            session.createQuery("DELETE FROM ChessOpening WHERE fen = :fen AND name = :name AND moves = :moves")
                    .setParameter("fen", chessOpening.getFen())
                    .setParameter("name", chessOpening.getName())
                    .setParameter("moves", chessOpening.getMoves())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) session.getTransaction().rollback();
        }
    }

    public ChessOpening getChessOpening(String fen, String name, String moves) {
        return session.createQuery("FROM ChessOpening WHERE fen = :fen AND name = :name AND moves = :moves", ChessOpening.class)
                .setParameter("fen", fen)
                .setParameter("name", name)
                .setParameter("moves", moves)
                .uniqueResult();
    }
}
