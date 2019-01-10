package service.impl;

import dao.SendDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SendDataService;

@Service
public class SendDataServiceImpl implements SendDataService {

    @Autowired
    SendDataDao sendDataDao;


    @Override
    public void insertQAData(String qaId, String userId, String qaContent, String qaVideo, String qa_time, String qaAudio) {

        sendDataDao.insertQa(qaId,userId,qaContent,qaVideo,qa_time,qaAudio);
    }
}
