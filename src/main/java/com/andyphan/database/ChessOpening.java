package com.andyphan.database;

import jakarta.persistence.*;

@Entity
@Table(name = "ChessOpenings", uniqueConstraints = @UniqueConstraint(columnNames = {"fen", "moves", "name"}))
public class ChessOpening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fen", nullable = false)
    private String fen;
    @Column(name = "moves", nullable = false)
    private String moves;
    @Column(name = "name", nullable = false)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public String getMoves() {
        return moves;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
