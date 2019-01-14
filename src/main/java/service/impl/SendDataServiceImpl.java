package service.impl;

import dao.SendDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SendDataService;
import utils.commonUtils;

@Service
public class SendDataServiceImpl implements SendDataService {

    @Autowired
    SendDataDao sendDataDao;


    @Override
    public void insertQAData(String qaId, String userId, String qaContent, String qaVideo, String qa_time, String qaAudio) {

        sendDataDao.insertQa(qaId,userId,qaContent,qaVideo,qa_time,qaAudio);
    }

    @Override
    public void addLikeQa(String userId, String qaId) {
        String qaLikeId = "qalike" + commonUtils.createUUID();
        sendDataDao.addLikeQaWithUser(qaLikeId,qaId,userId);
    }

    @Override
    public void removeLikeQa(String userId, String qaId) {
        sendDataDao.removeLikeQaWithUser(qaId,userId);
    }
}
