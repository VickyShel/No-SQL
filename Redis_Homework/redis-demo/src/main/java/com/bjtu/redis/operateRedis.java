package com.bjtu.redis;

import java.util.List;
import java.util.Map;

public class operateRedis {
    private static ReadJson readJson;
    private RedisOp redisOp;
    List<Map> counter;
    List<Map> action;

    public void increase_User_Num(){
        redisOp.showUserCnt();
        //真不行就不用list，变成一个大的String，然后split一下
//        System.out.println("Increase_User_Num:"+counter.get(0).get("valueFields"));
        redisOp.IncreaseCnt((List<String>) counter.get(0).get("keyFields"),Integer.parseInt((String) counter.get(0).get("valueFields")));
    }

    public void Decrease_User_Num(){
        redisOp.showUserCnt();
        redisOp.DecreaseCnt((List<String>) counter.get(1).get("keyFields"), Integer.parseInt((String) counter.get(1).get("valueFields")));
    }

    public void Show_User_Num(){
        redisOp.showUserCnt();
    }

    public void Show_AllUser_Freq(){
        redisOp.showAllUserFreq();
    }

    public void Show_SpecificUser_Freq(){
        String string=counter.get(4).get("valueFields").toString();
        String startTime=string.substring(0,12);
        String endTime=string.substring(13,25);
        redisOp.showSpecificUserFreq(startTime,endTime);
    }

    public void Show_All_ExitUser_Freq(){
        redisOp.showAllExitUserFreq();
    }

    public void Show_Specific_ExitUser_Freq(){
        String string=counter.get(6).get("valueFields").toString();
        String startTime=string.substring(0,12);
        String endTime=string.substring(13,25);
        redisOp.showSpecificExitUserFreq(startTime,endTime);
    }

    public void Delete_All_Data(){redisOp.deleteRedis();}

    public operateRedis(String counterPath,String actionPath){
        readJson=new ReadJson(counterPath,actionPath);
//        System.out.println(counterPath);
        redisOp=new RedisOp();
        counter=readJson.counter;
        action=readJson.action;
    }

    public void readAgain(){
        readJson.read();
        counter=readJson.counter;
        action=readJson.action;
    }


}
