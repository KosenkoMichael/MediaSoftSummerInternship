package Rooms;
public enum Prices {
    ECONOMY(50),
    STANDARD(100),
    LUX(200),
    ULTRA_LUX(500);

    private final int price;

    Prices(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
