package service;

public interface SendDataService {

    void insertQAData(String qaId,String userId,String qaContent,String qaVideo,String qa_time,String qaAudio);

    void addLikeQa(String userId,String qaId);
    void removeLikeQa(String userId,String qaId);
}
