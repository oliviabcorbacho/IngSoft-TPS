package Uno;

import java.util.Objects;

public abstract class Card {

    protected String color;

    public Card(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public boolean isPlayableOn(Card topCard) {
        return Objects.equals(this.color, topCard.getColor()) || Objects.equals(topCard.getColor(), "WILD");
    }

    public abstract void applyEffect(Game game);

}

