package com.bjtu.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RedisOp {
    JedisPool MyRedis;
    Jedis jedis;

    public RedisOp() {
        MyRedis=JedisInstance.getInstance();
//        jedis=new Jedis();
        jedis=MyRedis.getResource();
//        jedis.set("UserCnt","0");
//        System.out.println(jedis.get("UserCnt"));
    }

    public void IncreaseCnt(List<String> keyFields, int valueField){
        //先获取增加用户之前的用户数量
//        System.out.println(jedis.get("UserCnt"));
        int beforeNum;
        if(jedis.get("UserCnt")==null){
            beforeNum=0;
        }
        else{
            beforeNum=Integer.parseInt(jedis.get(keyFields.get(0)));
        }
        //然后给UserCnt增加valueField的数量
        jedis.incrBy(keyFields.get(0),valueField);
        //获取当前时间
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        //向UserFreq中添加valueField个个数的用户，包括用户的id以及用户进入直播间的时间
        //UserFreq中的id要通过先获取之前的用户数量然后再添加valueField个用户数量的id
        for(int i=0;i<valueField;i++){
            String string=time.format(date);
            jedis.hset(keyFields.get(1),"UserId"+(beforeNum+i+1),string);
            jedis.lpush("UserInList",string);
            jedis.sadd("UserInSet",string);
            jedis.zadd("UserInZSet",beforeNum,string);
        }
        System.out.println("直播间增加"+valueField+"名用户，当前用户数量为:"+jedis.get(keyFields.get(0)));
    }

    public void DecreaseCnt(List<String> keyFields,int valueField){
        //先获取增加用户之前的用户数量
//        int userNum=Integer.parseInt(jedis.get(keyFields.get(0)));
        //然后给UserCnt增加valueField的数量
        int beforeNum=Integer.parseInt(jedis.get(keyFields.get(0)));
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        String string=time.format(date);
        jedis.decrBy(keyFields.get(0),valueField);
        for(int i=0;i<valueField;i++){
//            jedis.hdel(keyFields.get(1),"UserId"+userNum);
//            userNum--;
            jedis.lpush("UserOutList",string);
            jedis.sadd("UserOutSet",string);
            jedis.zadd("UserOutZSet",beforeNum,string);
        }
        System.out.println("直播间减少"+valueField+"名用户，当前用户数量为:"+jedis.get(keyFields.get(0)));
    }

    public void showUserCnt(){
        if(jedis.get("UserCnt")==null){
            System.out.println("当前用户数量为：0");
        }
        else{
            System.out.println("当前用户数量为："+jedis.get("UserCnt"));
        }
//        System.out.println(jedis.get("UserCnt"));
        //输出用户数量
    }

    public void showAllUserFreq(){
        Map<String, String> map1 = jedis.hgetAll("UserFreq");
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            System.out.println("用户 = " + entry.getKey() + ", 进入时间 = " + entry.getValue());
        }
        //输出所有存在的用户信息
    }

    public void showSpecificUserFreq(String startTime,String endTime){
//        String startTime=valueFields.substring(0,12);
//        System.out.println(startTime);
//        String endTime=valueFields.substring(12,24);
//        System.out.println(endTime);
//        Map<String, String> map = jedis.hgetAll(keyField);
//        for(String key : map.keySet()){
//            String value=map.get(key);
//            if(value.compareTo(startTime)>=0 && value.compareTo(endTime)<=0){
//                //可以输出此用户元素
//            }
//        }
        Map<String, String> map1 = jedis.hgetAll("UserFreq");
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            if(entry.getValue().compareTo(startTime)>=0 && entry.getValue().compareTo(endTime)<=0){
                //可以输出此用户元素
                System.out.println("用户 = " + entry.getKey() + ", 进入时间 = " + entry.getValue());
            }
        }
    }

    public void showAllExitUserFreq(){
        System.out.println("一共有"+jedis.llen("UserOutList")+"人次退出此直播间");
        //输出所有存在的用户信息
    }

    public void showSpecificExitUserFreq(String startTime,String endTime){
//        String startTime=valueFields.substring(0,12);
//        System.out.println(startTime);
//        String endTime=valueFields.substring(12,24);
//        System.out.println(endTime);
//        Map<String, String> map = jedis.hgetAll(keyField);
//        for(String key : map.keySet()){
//            String value=map.get(key);
//            if(value.compareTo(startTime)>=0 && value.compareTo(endTime)<=0){
//                //可以输出此用户元素
//            }
//        }
        for (int i = 0; i < jedis.llen("UserOutList"); i++) {
//            System.out.println(jedis.lindex("UserOutList",i));
            if(jedis.lindex("UserOutList",i).compareTo(startTime)>=0 && jedis.lindex("UserOutList",i).compareTo(endTime)<=0){
                //可以输出此用户元素
                System.out.println("有用户在"+jedis.lindex("UserOutList",i)+"时刻退出");
            }
        }
    }

    public void deleteRedis(){
        jedis.del("UserFreq");
        jedis.del("UserCnt");
        jedis.del("UserInSet");
        jedis.del("UserInList");
        jedis.del("UserInZSet");
        jedis.del("UserOutSet");
        jedis.del("UserOutList");
        jedis.del("UserOutZSet");
    }

//    public void operateCounter(String keyField,String operateName,String valueField){
//
//    }
}
