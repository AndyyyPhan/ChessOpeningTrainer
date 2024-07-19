package com.andyphan.database;

import com.andyphan.chessopeningtrainer.ActiveChessOpening;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.*;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final SessionFactory sessionFactory;

    public DatabaseManager() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }

    public boolean isUserExisting(String username) {
        try (Session session = sessionFactory.openSession()){
            User existingUser = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
            return existingUser != null;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isValidUser(String username, String password) {
        if (!isUserExisting(username)) return false;

        try (Session session = sessionFactory.openSession()) {
            User existingUser = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
            return existingUser.getPassword().equals(password);
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validPasswordLength(String password) {
        return password.length() >= 8;
    }

    public void addUser(String username, String password) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            session.persist(user);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction != null) transaction.rollback();
        }
    }

    public User getUser(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE username =:username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ChessOpening> getOpeningsInPractice() {
        List<ChessOpening> practiceOpenings = Collections.emptyList();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User currentUser = session.get(User.class, UserManager.getCurrentUser().getId());
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ActiveChessOpening> criteriaQuery = criteriaBuilder.createQuery(ActiveChessOpening.class);
            Root<ActiveChessOpening> root = criteriaQuery.from(ActiveChessOpening.class);
            criteriaQuery.where(criteriaBuilder.equal(root.get("user"), currentUser));
            TypedQuery<ActiveChessOpening> typedQuery = session.createQuery(criteriaQuery);
            List<ActiveChessOpening> practiceOpeningsList = typedQuery.getResultList();
            practiceOpenings = practiceOpeningsList.stream()
                    .map(ActiveChessOpening::getChessOpening)
                    .collect(Collectors.toList());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
        return practiceOpenings;
    }

    public void persistChessOpening(ChessOpening chessOpening) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            ChessOpening existingOpening = session.createQuery("FROM ChessOpening WHERE fen = :fen", ChessOpening.class)
                    .setParameter("fen", chessOpening.getFen())
                    .uniqueResult();
            if (existingOpening == null) {
                transaction = session.beginTransaction();
                session.persist(chessOpening);
                transaction.commit();
            }
            else {
                chessOpening.setId(existingOpening.getId());
            }
        } catch (PersistenceException e) {
            if (transaction != null) transaction.rollback();
        }
    }

    public void addChessOpeningToPractice(ChessOpening chessOpening) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User currentUser = session.get(User.class, UserManager.getCurrentUser().getId());
            ChessOpening persistentChessOpening = session.get(ChessOpening.class, chessOpening.getId());

            ActiveChessOpening activeChessOpening = new ActiveChessOpening();
            activeChessOpening.setUser(currentUser);
            activeChessOpening.setChessOpening(persistentChessOpening);
            session.persist(activeChessOpening);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }

    public boolean isChessOpeningInPractice(ChessOpening chessOpening) {
        boolean isInPractice = false;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User currentUser = session.get(User.class, UserManager.getCurrentUser().getId());

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ActiveChessOpening> criteriaQuery = criteriaBuilder.createQuery(ActiveChessOpening.class);
            Root<ActiveChessOpening> root = criteriaQuery.from(ActiveChessOpening.class);

            criteriaQuery.select(root)
                    .where(
                            criteriaBuilder.and(
                                    criteriaBuilder.equal(root.get("user"), currentUser),
                                    criteriaBuilder.equal(root.get("chessOpening"), chessOpening)
                            )
                    );
            TypedQuery<ActiveChessOpening> query = session.createQuery(criteriaQuery);
            List<ActiveChessOpening> results = query.getResultList();
            isInPractice = !results.isEmpty();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
        return isInPractice;
    }

    public void saveChessOpening(ChessOpening chessOpening) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User currentUser = session.get(User.class, UserManager.getCurrentUser().getId());
            ChessOpening persistentChessOpening = session.get(ChessOpening.class, chessOpening.getId());

            UserChessOpening userChessOpening = new UserChessOpening();
            userChessOpening.setUser(currentUser);
            userChessOpening.setChessOpening(persistentChessOpening);
            session.persist(userChessOpening);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }

    public List<ChessOpening> getUserChessOpenings() {
        List<ChessOpening> userChessOpenings = Collections.emptyList();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User currentUser = session.get(User.class, UserManager.getCurrentUser().getId());
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<UserChessOpening> criteriaQuery = criteriaBuilder.createQuery(UserChessOpening.class);
            Root<UserChessOpening> root = criteriaQuery.from(UserChessOpening.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), currentUser));
            TypedQuery<UserChessOpening> typedQuery = session.createQuery(criteriaQuery);
            List<UserChessOpening> userChessOpeningList = typedQuery.getResultList();
            userChessOpenings = userChessOpeningList.stream()
                    .map(UserChessOpening::getChessOpening)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
        return userChessOpenings;
    }

    public String getNextCreatedOpeningFen() {
        return "CREATED_" + countUserChessOpenings();
    }

    private long countUserChessOpenings() {
        long count = 0;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, UserManager.getCurrentUser().getId());
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<UserChessOpening> root = criteriaQuery.from(UserChessOpening.class);
            criteriaQuery.select(criteriaBuilder.count(root));
            criteriaQuery.where(criteriaBuilder.equal(root.get("user"), user));
            TypedQuery<Long> query = session.createQuery(criteriaQuery);
            count = query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
        return count;
    }

//    public void addChessOpening(ChessOpening chessOpening) throws IOException {
//        Transaction transaction = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            if (chessOpening.getFen().isBlank()) chessOpening.setFen(ChessOpeningsParser.getNextCreatedOpeningFen());
//            User currentUser = UserManager.getCurrentUser();
//            chessOpening.setUser(currentUser);
//            session.persist(chessOpening);
//            transaction.commit();
//        } catch (PersistenceException e) {
//            e.printStackTrace();
//            if (transaction != null) transaction.rollback();
//        }
//    }
//
//    public boolean isChessOpeningExisting(ChessOpening chessOpening) {
//        ChessOpening existingChessOpening = session.createQuery("FROM ChessOpening WHERE fen = :fen", ChessOpening.class)
//                .setParameter("fen", chessOpening.getFen())
//                .uniqueResult();
//        return existingChessOpening != null;
//    }
//
//    public void deleteChessOpening(ChessOpening chessOpening) {
//        session.beginTransaction();
//        try {
//            session.createQuery("DELETE FROM ChessOpening WHERE fen = :fen AND name = :name AND moves = :moves")
//                    .setParameter("fen", chessOpening.getFen())
//                    .setParameter("name", chessOpening.getName())
//                    .setParameter("moves", chessOpening.getMoves())
//                    .executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (session.getTransaction() != null) session.getTransaction().rollback();
//        }
//    }
//
//    public ChessOpening getChessOpening(String fen, String name, String moves) {
//        return session.createQuery("FROM ChessOpening WHERE fen = :fen AND name = :name AND moves = :moves", ChessOpening.class)
//                .setParameter("fen", fen)
//                .setParameter("name", name)
//                .setParameter("moves", moves)
//                .uniqueResult();
//    }
//
    public void loginUser(String username, String password) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("username"), username),
                            criteriaBuilder.equal(root.get("password"), password)
                    )
            );
            TypedQuery<User> query = session.createQuery(criteriaQuery);
            User user = query.getSingleResult();
            if (user != null) {
                UserManager.setCurrentUser(user);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }
}
