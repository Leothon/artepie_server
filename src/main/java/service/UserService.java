package service;

import entity.User;

public interface UserService {

    boolean phoneExits(String phoneNumber);

    void register(String uuid,String phoneNumber,String token,String username,String registerTime);

    String returnTokenByPhone(String number);

    User getUserInfoById(String id);

    String getTokenByUID(String userId);

    void updateInfo(User user);

    void qqRegister(String uuid,String userIcon,String userName,int sex,String registerTime,String token,String tencentToken);

    User getUserInfoByQQ(String accessToken);

    void insertPhoneNumber(String uuid,String phoneNumber);
    String getUserIdByPhoneNumber(String phoneNumber);

    void insertPassword(String uuid,String password);
}
