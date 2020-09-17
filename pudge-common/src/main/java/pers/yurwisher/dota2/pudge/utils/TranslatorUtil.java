/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package pers.yurwisher.dota2.pudge.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author yq
 * 2020年9月14日 17:16:56
 * 翻译工具类
 */
public class TranslatorUtil {

    private static final String GOOGLE_TRANSLATE_API_URL = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=zh-CN&dt=t";

    /**默认超时时间*/
    private static final int DEFAULT_TIME_OUT = 8 * 1000 ;
    /**
     * 英文转中文
     * @param word 英文句子或单词
     * @param timeout 超时时间 毫秒
     * @return 首个匹配
     */
    public static String englishToChinese(String word,int timeout){
        String result = HttpUtil.get(GOOGLE_TRANSLATE_API_URL,new JSONObject().fluentPut("q",word),timeout);
        if(StrUtil.isNotEmpty(result)){
            JSONArray array = JSON.parseArray(result);
            array = CollectionUtil.isNotEmpty(array) ? array.getJSONArray(0) : null;
            array = CollectionUtil.isNotEmpty(array) ? array.getJSONArray(0) : null;
            return  CollectionUtil.isNotEmpty(array) ? array.getString(0) : null;
        }
        return null;
    }

    /**
     * 英文转中文
     * @param word 英文句子或单词
     * @return 首个匹配
     */
    public static String englishToChinese(String word){
        return englishToChinese(word,DEFAULT_TIME_OUT);
    }
}
