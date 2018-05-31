package lt.vu.mif.model.order;

public enum OrderStatus {
    ACCEPTED("Priimtas"),
    ELECTED("Renkamas"),
    SENT("Išsiųstas"),
    DELIVERED("Pristatytas");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}