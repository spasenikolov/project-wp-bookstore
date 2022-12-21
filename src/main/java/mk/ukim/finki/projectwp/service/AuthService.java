package mk.ukim.finki.projectwp.service;

import mk.ukim.finki.projectwp.model.User;

public interface AuthService {

    User login(String username, String password);


}
