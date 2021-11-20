package br.com.refactoring.domain;

public class Plays {

    private Play hamlet;
    private Play asLike;
    private Play othello;

    public Play getOthello() {
        return othello;
    }

    public void setOthello(Play othello) {
        this.othello = othello;
    }

    public Play getAsLike() {
        return asLike;
    }

    public void setAsLike(Play asLike) {
        this.asLike = asLike;
    }

    public Play getHamlet() {
        return hamlet;
    }

    public void setHamlet(Play hamlet) {
        this.hamlet = hamlet;
    }
}
