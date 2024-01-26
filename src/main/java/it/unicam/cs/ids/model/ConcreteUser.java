package it.unicam.cs.ids.model;

public class ConcreteUser implements User {

    private String id;

    private String email;

    private String password;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
