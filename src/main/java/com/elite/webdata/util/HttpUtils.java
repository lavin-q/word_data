package com.elite.webdata.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2019/9/29 16:40
 */
@Slf4j
public class HttpUtils {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    /**
     * post请求(用于key-value格式的参数)
     *
     * @param url
     * @param params
     * @return
     */
    public static PostResult doPost(Map<String, String> params, String url) throws IOException  {
        return doPost(params,url,null);
    }

    /**
     *
     * @param params
     * @param url
     * @param headerMap
     * @return
     * @throws IOException
     */
    public static PostResult doPost(Map<String, String> params, String url, Map<String, String> headerMap) throws IOException {

        RequestBody body = buildRequestBody(params);
        //第二步构建Request对象
        Request request = buildRequest(url,headerMap)
                .post(body)
                .build();
        //第三步构建Call对象
        //final Call call = client.newCall(request);
        //第四步:同步get请求
        //Response response = call.execute();
        PostResult result = executeHttp(request);
        return result;
    }

    private static PostResult executeHttp(Request request) {

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        PostResult result = new PostResult();
        try (Response response = client.newCall(request).execute()) {
            result.setCode(response.code());
            result.setResponsebody(response.body().string());
            result.setSuccess(true);
            log.debug("response:{}", result);
        }catch (Exception e){
            result.setSuccess(false);
        }
        return result;
    }


    private static Request.Builder buildRequest(String url, Map<String, String> headerMap) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if(headerMap!=null){
            for(String key:headerMap.keySet()){
                builder.addHeader(key,headerMap.get(key));
            }
        }
        return builder;
    }

    private static RequestBody buildRequestBody(Map<String, String> params) {

        FormBody.Builder builder =  new FormBody.Builder();
        for(String key:params.keySet()){
            builder.add(key,params.get(key));
        }
        return builder.build();
    }
    /**
     * post请求(用于Json格式的参数)
     *
     * @param url
     * @param json
     * @return
     */
    public static PostResult doPost(String json, String url) throws IOException {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        Request request = new Request.Builder()
                .url(url)//请求的url
                .post(requestBody)
                .build();
        PostResult result = executeHttp(request);
        return result;
    }

    /**
     * post请求(用于Json格式的参数)
     *
     * @param url
     * @param json
     * @return
     */
    public static PostResult doPost(String json, String url, Map<String, String> headerMap) throws IOException {

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        Request request = buildRequest(url,headerMap)
                .url(url)//请求的url
                .post(requestBody)
                .build();
        PostResult result = executeHttp(request);
        return result;
    }

    /**
     * get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static PostResult doGet(String url) throws IOException {
        return doGet(url, null);
    }




    /**
     * Https获取结果
     *
     * @param request
     * @return
     */
    private static PostResult executeHttps(Request request) {
        PostResult results = new PostResult();
        try {

            Response execute = OKHttpClientBuilder.buildOKHttpClient()
                    .build()
                    .newCall(request).execute();
            results.setCode(execute.code());
            results.setResponsebody(execute.body().string());
            results.setSuccess(true);
        } catch (IOException e) {
            results.setSuccess(false);
        }
        return results;
    }


    /**
     * Https
     * post请求(用于key-value格式的参数)
     *
     * @param params
     * @param url
     * @return
     * @throws IOException
     */
    public static PostResult httpsDoPost(Map<String, String> params, String url) throws IOException {
        return httpsDoPost(params, url, null);
    }

    public static PostResult httpsDoPost(Map<String, String> params, String url, Map<String, String> headerMap) {
        RequestBody body = buildRequestBody(params);
        Request request = buildRequest(url, headerMap)
                .post(body)
                .build();
        PostResult result = executeHttps(request);
        return result;
    }

    /**
     * Https
     * post请求(用于Json格式的参数)
     *
     * @param url
     * @param json
     * @return
     */
    public static PostResult httpsDoPost(String json, String url, Map<String, String> headerMap) throws IOException {

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        Request request = buildRequest(url, headerMap)
                .url(url)//请求的url
                .post(requestBody)
                .build();
        PostResult result = executeHttps(request);
        return result;
    }


    public static PostResult doGet(String url, Map<String, String> headerMap) throws IOException {
        Request request = buildRequest(url, headerMap)
                .get()
                .build();
        //第三步构建Call对象
        //final Call call = client.newCall(request);
        //第四步:同步get请求
        //Response response = call.execute();
        PostResult result = executeHttp(request);
        return result;
    }

    /**
     * WeService发送post请求
     * @param url
     * @param soapBody
     * @return
     */
    public static PostResult wsDoPost(String url, String soapBody) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = FormBody.create(MediaType.parse("text/xml; charset=utf-8")
                , soapBody);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        PostResult result = new PostResult();
        try (Response response = client.newCall(request).execute()) {
            result.setCode(response.code());
            result.setResponsebody(response.body().string());
            result.setSuccess(true);
            //log.debug("response:{}", result);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }
}
