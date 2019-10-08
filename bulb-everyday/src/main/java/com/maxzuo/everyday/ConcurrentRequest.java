package com.maxzuo.everyday;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发请求
 * <p>
 * Created by zfh on 2019/10/08
 */
public class ConcurrentRequest {

    private static ExecutorService pool = Executors.newCachedThreadPool();

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        // String ids = "41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,68,69,70,71,72,73,74,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,106,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,277,278,279,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,314,315,316,317,318,319,320,321,322,323,324,326,327,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360,361,362,363,364,365,366,367";
        String ids = "368,369,370,371,372,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400,401,402,404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429";
        String[] userIds = ids.split(",");
        for (String id : userIds) {
            int userId = Integer.valueOf(id);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    send(userId);
                }
            });
        }

    }

    /**
     * POST 请求
     */
    private static void send (Integer userId) {
        Map<String, Integer> data = new HashMap<>(16);
        data.put("activityId", 175);
        data.put("bdId", 49);
        data.put("payType", 3);
        data.put("customerId", userId);
        data.put("inletShop", 0);
        data.put("recommendUserId", 0);

        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("cmd", "bdistrict/businessDistrictUnionActivityPlaceOrder");
        formBody.add("data", JSONObject.toJSONString(data));
        formBody.add("version", "1");

        Request request = new Request.Builder()
                .url("http://app.xxx.com:81/zxcity_restful/ws/rest")
                .header("apikey", "test")
                .post(formBody.build())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                byte[] bytes = response.body().bytes();
                System.out.println(count.incrementAndGet() + " - " + new String(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
