package com.bjtu.redis;

import java.io.*;
import java.util.*;
import com.alibaba.fastjson.JSON;

public class ReadJson {
    private String counterJson;
    private String counterPath;
    private String actionPath;
    private String actionJson;
    public List<Map> counter;
    public List<Map> action;

    //构造函数，传入action以及counter的json文件路径
    public ReadJson(String counterPath,String actionPath){
//        System.out.println(counterPath);
        this.counterPath=counterPath;
        this.actionPath=actionPath;
        counterJson=readJsonFile(counterPath);
        actionJson=readJsonFile(actionPath);
        counter = JSON.parseArray(counterJson, Map.class);
        action = JSON.parseArray(actionJson, Map.class);
    }

    public void read(){
        counterJson=readJsonFile(counterPath);
        actionJson=readJsonFile(actionPath);
        counter = JSON.parseArray(counterJson, Map.class);
        action = JSON.parseArray(actionJson, Map.class);
//        System.out.println(counter.get(0).get("valueFields"));
    }

    //将json文件转为String，以方便后面进行反序列化
    public String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //假如文件路径改变并且需要重读，则调用此方法
    public void changePath(String counterPath,String actionPath) {
        counterJson = readJsonFile(counterPath);
        actionJson = readJsonFile(actionPath);
        counter = JSON.parseArray(counterJson, Map.class);
        action = JSON.parseArray(actionJson, Map.class);
    }
}

