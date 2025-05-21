package Uno;

import java.util.Objects;

public abstract class Card {

    protected String color;
    protected String content;

    public Card(String color, String content) {
        this.color = color;
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public String getContent(){ return content; }

    public boolean isPlayableOn(Card topCard){
        return (topCard.content.equals(this.content)  || topCard.color.equals(this.color));
    }


    public abstract void applyEffect(Game game);

}

