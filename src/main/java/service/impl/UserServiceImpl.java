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
        userDao.updateUserInfo(user.getUser_name(),user.getUser_sex(),user.getUser_birth(),user.getUser_phone(),user.getUser_signal(),user.getUser_address(),userId);
    }
}
