package com.bjtu.redis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RedisTest {
    static operateRedis operate;

    public static void main(String[] args) throws Exception {
        String counterPath ="./src/main/resources/Counter";
        String actionPath ="./src/main/resources/Action";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        operate=new operateRedis(counterPath,actionPath);

        FileMonitor m = new FileMonitor(500);//设置监控的间隔时间，初始化监听
        m.monitor("src/main/resources", new FileListener(operate)); //指定文件夹，添加监听
        m.start();//开启监听

        while(true) {
            System.out.println("请选择您要进行的操作:");
            System.out.println("1.查看所有action");
            System.out.println("2.执行action");
            System.out.println("3.退出");
            String input = br.readLine();
            if(input.equals("1")){
                System.out.println("1.Increase_User_Num");
                System.out.println("2.Decrease_User_Num");
                System.out.println("3.Show_Now_User_Num");
                System.out.println("4.Show_All_InUser_Freq");
                System.out.println("5.Show_Specific_InUser_Freq");
                System.out.println("6.Show_All_ExitUser_Freq");
                System.out.println("7.Show_Specific_ExitUser_Freq");
                System.out.println("8.Delete_All_Data");
                continue;
            }
            else if(input.equals("2")){
                System.out.println("请输入您要执行操作的序号:");
                String num= br.readLine();
                switch (num){
                    case "1":operate.increase_User_Num();break;
                    case "2":operate.Decrease_User_Num();break;
                    case "3":operate.Show_User_Num();break;
                    case "4":operate.Show_AllUser_Freq();break;
                    case "5":operate.Show_SpecificUser_Freq();break;
                    case "6":operate.Show_All_ExitUser_Freq();break;
                    case "7":operate.Show_Specific_ExitUser_Freq();break;
                    case "8":operate.Delete_All_Data();break;
                }
                continue;
            }
            else{
                System.out.println("程序已终止！");
                System.exit(0);
            }
        }
    }
}
