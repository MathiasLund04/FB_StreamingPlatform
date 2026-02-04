package Model;

public class User {
    private int id;
    private String email;
    private String name;
    private String SubscriptionType;

    public User() {}

    public User(int id, String email, String name, String SubscriptionType) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.SubscriptionType = SubscriptionType;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSubscriptionType() { return SubscriptionType; }
    public void setSubscriptionType(String subscriptionType) { this.SubscriptionType = subscriptionType; }
}
