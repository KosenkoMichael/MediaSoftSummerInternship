package Rooms;
public class EconomyRoom extends Room {
    public EconomyRoom(int number) {
        super(number, (int)(Math.random() * 3) + 1, Prices.ECONOMY.getPrice());
    }
}
