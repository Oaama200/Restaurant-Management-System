package org.example;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomLinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        int id;

        Node(T data, int id) {
            this.data = data;
            this.next = null;
            this.id = id;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    public CustomLinkedList() {
        head = null;
        size = 0;
    }

    public void add(T data, int id) {
        Node<T> newNode = new Node<>(data, id);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public T getById(int id) {
        Node<T> current = head;
        while (current != null) {
            if (current.id == id) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public boolean removeById(int id) {
        if (head == null) return false;

        if (head.id == id) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> current = head;
        Node<T> prev = null;

        while (current != null && current.id != id) {
            prev = current;
            current = current.next;
        }

        if (current != null) {
            prev.next = current.next;
            size--;
            return true;
        }

        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}