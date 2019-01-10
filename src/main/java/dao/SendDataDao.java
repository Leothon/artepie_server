package dao;

import org.apache.ibatis.annotations.Param;

public interface SendDataDao {



    void insertQa(@Param("qaId") String qaId, @Param("userId") String userId,@Param("qaContent") String qaContent,@Param("qaVideo") String qaVideo,@Param("qaTime") String qaTime,@Param("qaAudio") String qaAudio);
}
