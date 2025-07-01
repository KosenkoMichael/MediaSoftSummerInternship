package Rooms;
public abstract class Room {
    protected int number;
    protected int maxPeople;
    protected int pricePerNight;
    protected boolean isReserved;

    public Room(int number, int maxPeople, int pricePerNight) {
        this.number = number;
        this.maxPeople = maxPeople;
        this.pricePerNight = pricePerNight;
        this.isReserved = false;
    }

    public int getNumber() {
        return number;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        this.isReserved = reserved;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
        + " number " + number
        + " maxPeople " + maxPeople
        + " pricePerNight " + pricePerNight
        + " isReserved " + isReserved;
    }
}
