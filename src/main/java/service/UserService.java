package service;

import entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {

    boolean phoneExits(String phoneNumber);

    void register(String uuid,String phoneNumber,String token,String username,String registerTime);

    String returnTokenByPhone(String number);

    User getUserInfoById(String id);

    String getTokenByUID(String userId);

    void updateInfo(User user);

    void qqRegister(String uuid,String userIcon,String userName,int sex,String registerTime,String token,String tencentToken,String phone);
    void weChatRegister(String uuid,String userIcon,String userName,int sex,String location,String registerTime,String token,String tencentToken,String phone);

    User getUserInfoByQQ(String accessToken);

    void insertPhoneNumber(String uuid,String phoneNumber);
    String getUserIdByPhoneNumber(String phoneNumber);

    void insertPassword(String uuid,String password);

    String getPasswordByPhoneNumber(String phoneNumber);

    void updateToken(String token,String uuid);

    void insertFalseUser(String uuid,String username,String token,String phone);

    void insertFalseUserWithTime(String uuid,String username,String token,String phone,String time);



    String getTokenByAccessToken(String accessToken);
}
