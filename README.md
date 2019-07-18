# toutao
This is a project of the display of headlines based on spring boot.

- #### 如何调用远程服务

```java
public List<Interviewee> getInterviewee(long interviewer){
        String corpMailPre = collectionDao.getUserById(interviewer).getUrs();
        if (corpMailPre == null){
            throw new BusinessException("面试官信息不存在！",-1);
        }

        String project_id = "1000000345";
        String day = "2019-07-17";
        String timestamp = "1563269127";
        String sign = "12212";

        String message = "";
        StringBuffer buffer = new StringBuffer(URL);
        buffer.append("?").append(PROJECT_ID).append("=").append(project_id).append("&").append(CORP_MAIL_PRE).
                append("=").append(corpMailPre).append("&").append(DAY).append("=").append(day).append("&").
                append(TIMRESTAMP).append("=").append(timestamp).append("&").append(SIGN).append("=").append(sign);
        try {
            URL url = new URL(buffer.toString());
            //得到connection对象。
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //连接
            connection.connect();
            //得到响应码
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                //得到响应流
                InputStream inputStream = connection.getInputStream();
                //获取响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null){
                    message = line;
                }
                reader.close();
                connection.disconnect();
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage(), ReturnUtils.RES_CODE_SERVER_ERROR);
        }
        String str1 = message.substring(message.indexOf("["));
        String json = str1.substring(0, str1.length() - 2);
        List<Interviewee> list = JsonUtil.getListFromJson(json, Interviewee.class);
        return list;
    }
```

描述：通过对象给定的URL接口，调用接口的返回值，获取相关信息；