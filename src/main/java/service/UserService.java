package service;

import entity.User;

public interface UserService {

    boolean phoneExits(String phoneNumber);

    void register(String uuid,String phoneNumber,String token,String username,String registerTime);

    String returnTokenByPhone(String number);

    User getUserInfoById(String id);

    String getTokenByUID(String userId);
}
