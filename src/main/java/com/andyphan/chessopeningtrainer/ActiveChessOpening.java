package com.andyphan.chessopeningtrainer;

import com.andyphan.database.ChessOpening;
import com.andyphan.database.User;
import jakarta.persistence.*;

@Entity
@Table(name = "ActiveChessOpenings")
public class ActiveChessOpening {
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
