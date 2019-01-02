package dao;


import org.apache.ibatis.annotations.Param;

public interface UserDao {

    /**
     * 获取手机号，不存在返回0
     * @param phoneNumber
     * @return
     */
    int getPhoneNumber(String phoneNumber);


    void insertPhoneTokenUuid(@Param("uuid") String uuid, @Param("phoneNumber") String phoneNumber, @Param("token") String token, @Param("username") String username);

    String getTokenByPhoneNumber(String phoneNumber);
}
