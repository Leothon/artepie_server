package service.impl;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;

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
}
