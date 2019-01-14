package dao;

import org.apache.ibatis.annotations.Param;

public interface SendDataDao {



    void insertQa(@Param("qaId") String qaId, @Param("userId") String userId,@Param("qaContent") String qaContent,@Param("qaVideo") String qaVideo,@Param("qaTime") String qaTime,@Param("qaAudio") String qaAudio);

    void addLikeQaWithUser(@Param("qaLikeId") String qaLikeId,@Param("qaLikeQaId") String qaLikeQaId,@Param("qaLikeUserId") String qaLikeUserId);
    void removeLikeQaWithUser(@Param("qaLikeQaId") String qaLikeQaId,@Param("qaLikeUserId") String qaLikeUserId);


}
