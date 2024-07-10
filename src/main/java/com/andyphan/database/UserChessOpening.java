package com.andyphan.database;

import jakarta.persistence.*;

@Entity
@Table(name = "UserChessOpenings")
public class UserChessOpening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "chess_opening_id", nullable = false)
    private ChessOpening chessOpening;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChessOpening getChessOpening() {
        return chessOpening;
    }

    public void setChessOpening(ChessOpening chessOpening) {
        this.chessOpening = chessOpening;
    }
}
