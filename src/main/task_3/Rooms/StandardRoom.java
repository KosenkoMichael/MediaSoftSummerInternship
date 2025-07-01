package Rooms;
public class StandardRoom extends ProRoom {
    public StandardRoom(int number) {
        super(number, (int)(Math.random() * 3) + 2, Prices.STANDARD.getPrice());
    }
}
