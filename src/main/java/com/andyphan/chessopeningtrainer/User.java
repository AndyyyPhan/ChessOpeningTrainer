package com.andyphan.chessopeningtrainer;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "Username", nullable = false, unique = true)
    private String username;
    @Column(name = "Password", nullable = false)
    private String password;
    @OneToMany(mappedBy = "user")
    private List<ChessOpening> chessOpenings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ChessOpening> getChessOpenings() {
        return chessOpenings;
    }

    public void setChessOpenings(List<ChessOpening> chessOpenings) {
        this.chessOpenings = chessOpenings;
    }
}
