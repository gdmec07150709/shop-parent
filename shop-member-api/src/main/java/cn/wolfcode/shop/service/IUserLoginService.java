package cn.wolfcode.shop.service;

public interface IUserLoginService {
    void register(String username,String password);

    String login(String username, String password);

    void logout(String token);
}
