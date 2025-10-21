package com.duke;


public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("HBHBKBKJBK", 10);
        HashMap <Integer,String>map2 = new HashMap<>();
        map2.put(1,"HKBjndcaonc");
        map2.put(2,"HKBjnшдтшдdcaonc");
        map2.remove(1);

        System.out.println(map.get("HBHBKBKJBK"));
        System.out.println(map2.get(2));
    }
}