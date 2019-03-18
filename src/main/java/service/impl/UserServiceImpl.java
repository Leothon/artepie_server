package service.impl;

import dao.UserDao;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;
import utils.tokenUtils;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    public UserDao userDao;


    @Override
    public boolean phoneExits(String phoneNumber) {

        int result = userDao.getPhoneNumber(phoneNumber);
        if (result == 1){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void register(String uuid, String phoneNumber, String token,String username,String registerTime) {
        userDao.insertPhoneTokenUuid(uuid,phoneNumber,token,username,registerTime);
    }

    @Override
    public String returnTokenByPhone(String number) {

        return userDao.getTokenByPhoneNumber(number);
    }

    @Override
    public User getUserInfoById(String id) {
        return userDao.getUserInfo(id);
    }

    @Override
    public String getTokenByUID(String userId) {
        return userDao.getTokenByUid(userId);
    }

    @Override
    public void updateInfo(User user) {
        String userId = tokenUtils.ValidToken(user.getUser_token()).getUid();
        if (user.getUser_icon() != null && !user.getUser_icon().equals("")){
            userDao.updateUserIcon(user.getUser_icon(),userId);
        }
        userDao.updateUserInfo(user.getUser_name(),user.getUser_sex(),user.getUser_birth(),user.getUser_phone(),user.getUser_signal(),user.getUser_address(),userId,user.getUser_password());
    }

    @Override
    public void qqRegister(String uuid, String userIcon, String userName, int sex, String registerTime, String token, String tencentToken) {
        userDao.insertQQRegister(uuid,userIcon,userName,sex,registerTime,token,tencentToken);
    }

    @Override
    public void weChatRegister(String uuid, String userIcon, String userName, int sex,String location, String registerTime, String token, String tencentToken) {
        userDao.insertWeChatRegister(uuid,userIcon,userName,sex,location,registerTime,token,tencentToken);
    }
    @Override
    public User getUserInfoByQQ(String accessToken) {

        return userDao.getUserInfoByQQ(accessToken);
    }

    @Override
    public void insertPhoneNumber(String uuid, String phoneNumber) {
        userDao.updatePhoneNumber(uuid,phoneNumber);
    }

    @Override
    public String getUserIdByPhoneNumber(String phoneNumber) {

        return userDao.getUserIdByPhoneNumber(phoneNumber);
    }

    @Override
    public void insertPassword(String uuid, String password) {

        userDao.insertPassword(uuid,password);
    }

    @Override
    public String getPasswordByPhoneNumber(String phoneNumber) {


        return userDao.getPasswordByPhoneNumber(phoneNumber);
    }

    @Override
    public void updateToken(String token, String uuid) {
        userDao.updateToken(token,uuid);
    }
}
