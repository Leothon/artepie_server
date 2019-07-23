package utils;


import entity.Bill;

import java.util.Comparator;

public class SortClass implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        // TODO 自动生成的方法存根
        Bill bill1 = (Bill) o1;
        Bill bill2 = (Bill) o2;
//		public int compareTo(String anotherString)
//		按字典顺序比较两个字符串。该比较基于字符串中各个字符的 Unicode 值。
//		按字典顺序将此 String 对象表示的字符序列与参数字符串所表示的字符序列进行比较。
//		如果按字典顺序此 String 对象位于参数字符串之前，则比较结果为一个负整数。
//		如果按字典顺序此 String 对象位于参数字符串之后，则比较结果为一个正整数。
//		如果这两个字符串相等，则结果为 0；compareTo 只在方法 equals(Object) 返回 true 时才返回 0。

        int flag = bill1.getTime().compareTo(bill2.getTime());

        return flag;
    }

}

