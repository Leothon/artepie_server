package dao;


import entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    /**
     * 获取手机号，不存在返回0
     * @param phoneNumber
     * @return
     */
    int getPhoneNumber(String phoneNumber);


    void insertPhoneTokenUuid(@Param("uuid") String uuid, @Param("phoneNumber") String phoneNumber, @Param("token") String token, @Param("username") String username,@Param("registerTime") String registerTime);

    String getTokenByPhoneNumber(String phoneNumber);

    User getUserInfo(String userId);

    String getTokenByUid(String userId);
}
