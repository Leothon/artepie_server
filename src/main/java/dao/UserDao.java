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

    void updateUserInfo(@Param("username") String username,@Param("userSex") int userSex,@Param("userBirth") String userBirth,@Param("userPhone") String userPhone,@Param("userSignal") String userSignal,@Param("userAddress") String userAddress,@Param("userId") String userId,@Param("userPassword") String passWord);

    void updateUserIcon(@Param("userIcon") String userIcon,@Param("userId") String userId);

    void insertQQRegister(@Param("uuid") String uuid,@Param("userIcon")  String userIcon,@Param("userName")  String userName,@Param("sex")  int sex,@Param("registerTime")  String registerTime,@Param("token")  String token,@Param("tencentToken")  String tencentToken);

    void insertWeChatRegister(@Param("uuid") String uuid,@Param("userIcon")  String userIcon,@Param("userName")  String userName,@Param("sex")  int sex,@Param("location") String location,@Param("registerTime")  String registerTime,@Param("token")  String token,@Param("tencentToken")  String tencentToken);

    User getUserInfoByQQ(String accessToken);

    void updatePhoneNumber(@Param("userId") String uuid,@Param("phoneNumber") String phoneNumber);

    String getUserIdByPhoneNumber(String phoneNumber);

    void insertPassword(@Param("uuid") String uuid,@Param("password") String password);

    String getPasswordByPhoneNumber(String phoneNumber);

}
