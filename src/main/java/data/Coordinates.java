package data;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private static final long serialVersionUID = 32L;
    private long x; //Максимальное значение поля: 768
    private Integer y; //Поле не может быть null

    public Coordinates(long x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
