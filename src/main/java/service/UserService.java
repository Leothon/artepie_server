package service;

public interface UserService {

    boolean phoneExits(String phoneNumber);

    void register(String uuid,String phoneNumber,String token,String username);

    String returnTokenByPhone(String number);
}
