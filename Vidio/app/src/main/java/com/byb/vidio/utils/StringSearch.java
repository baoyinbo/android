/**
 * 文 件 名:  StringSearch.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  2017/4/13
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Sunday算法匹配
 *
 * @author 江钰锋 00501
 * @version [版本号, 2017/4/13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StringSearch {
    private int currentPos = 0;
    // 匹配字符的Map,记录改匹配字符串有哪些char并且每个char最后出现的位移
    private Map<Character, Integer> map ;

    public StringSearch(String pattern) {
        pattern=pattern.toLowerCase();
        //Sunday匹配时，用来存储Pattern中每个字符最后一次出现的位置，从右到左的顺序
        map = new HashMap<Character, Integer>();
        for (int i = 0, plen = pattern.length(); i < plen; i++) {
            map.put(pattern.charAt(i), i);
        }
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    /**
     * Sunday匹配时，用来存储Pattern中每个字符最后一次出现的位置，从右到左的顺序
     * @param pattern
     */
    public void updateMap(String pattern){
        currentPos=0;
        pattern=pattern.toLowerCase();
        map.clear();
        for (int i = 0, plen = pattern.length(); i < plen; i++) {
            map.put(pattern.charAt(i), i);
        }
    }

    /**
     * Sunday匹配，假定Text中的K字符的位置为：当前偏移量+Pattern字符串长度+1
     *
     * @param source  目标字符串
     * @param pattern 指定字符串
     * @return 指定字符串在目标字符串中的位置
     */
    public int match(String source, String pattern) {
        pattern=pattern.toLowerCase();
        source=source.toLowerCase();
        int slen = source.length();
        int plen = pattern.length();

        // 当剩下的原串小于指定字符串时，匹配不成功
        if ((slen - currentPos) < plen) {
            return -1;
        }
        boolean isMatch=isMatchFromPos(source, pattern, currentPos);
        if (!isMatch) {//如果没有匹配成功
            int nextStartPos = currentPos + plen;

            // 如果移动位置正好是结尾，即是没有匹配到
            if ((nextStartPos) == slen) {
                return -1;
            }

            // 如果匹配的后一个字符没有在Pattern字符串中出现，则跳过整个Pattern字符串长度
            if (!map.containsKey(source.charAt(nextStartPos))) {
                currentPos = nextStartPos;
            } else {
                // 如果匹配的后一个字符在Pattern字符串中出现，则将该位置和Pattern字符串中的最右边相同字符的位置对齐
                currentPos = nextStartPos - map.get(source.charAt(nextStartPos));
            }

            return match(source, pattern);
        } else {
            return currentPos;
        }
    }

    /**
     * 检查从Text的指定偏移量开始的子串是否和Pattern匹配
     *
     * @param source  目标字符串
     * @param pattern 指定字符串
     * @param pos     起始位置
     * @return 是否匹配
     */
    private boolean isMatchFromPos(String source, String pattern, int pos) {
        for (int i = 0, plen = pattern.length(); i < plen; i++) {
            if (source.charAt(pos + i) != pattern.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
