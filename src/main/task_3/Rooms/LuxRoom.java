package Rooms;
public class LuxRoom extends ProRoom {
    public LuxRoom(int number) {
        super(number, (int)(Math.random() * 4) + 2, Prices.LUX.getPrice());
    }
}
