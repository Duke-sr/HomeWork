package com.duke;

import java.util.Objects;

public class HashMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CAPACITY = 16;

    private Node<K, V>[] table;
    private int size;
    private int capacity;
    private int threshold;

    public HashMap() {
        this.capacity = DEFAULT_CAPACITY;
        this.threshold = (int) (capacity * LOAD_FACTOR);
        this.table = new Node[capacity];
    }

    // Хэш функция
    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    // вставка элемента (с перезаписью при совпадении ключа)
    public void put(K key, V value) {
        int hash = hash(key);
        int index = (capacity - 1) & hash;

        Node<K, V> head = table[index];
        for (Node<K, V> e = head; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(e.key, key)) {
                e.value = value;
                return;
            }
        }

        // добавление нового узла в начало списка
        Node<K, V> newNode = new Node<>(hash, key, value, head);
        table[index] = newNode;
        size++;

        // Проверка на расширение
        if (size > threshold) resize();
    }

    //Получение по ключу
    public V get(K key) {
        int hash = hash(key);
        int index = (capacity - 1) & hash;

        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(e.key, key)) {
                return e.value;
            }
        }
        return null;
    }

    //Удаление по ключу
    public V remove(K key) {
        int hash = hash(key);
        int index = (capacity - 1) & hash;

        Node<K, V> prev = null;
        Node<K, V> e = table[index];

        while (e != null) {
            if (e.hash == hash && Objects.equals(e.key, key)) {
                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
            prev = e;
            e = e.next;
        }
        return null;
    }

    // Расширение таблицы в 2 раза
    private void resize() {
        int newCapacity = capacity * 2;
        Node<K, V>[] newTable = new Node[newCapacity];

        for (Node<K, V> e : table) {
            while (e != null) {
                Node<K, V> next = e.next;
                int newIndex = (newCapacity - 1) & e.hash;
                e.next = newTable[newIndex];
                newTable[newIndex] = e;
                e = next;
            }
        }

        table = newTable;
        capacity = newCapacity;
        threshold = (int) (capacity * LOAD_FACTOR);
    }

    public int size() {
        return size;
    }

    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}