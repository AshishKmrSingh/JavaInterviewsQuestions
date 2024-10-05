package org.example.collections;

import java.util.NoSuchElementException;

/**
 * Priority Queue Implementation using Ordered List
 */
public class PriorityQueueOrderedList<E> {
    // Node class representing each element in the queue
    private static class Node<E> {
        E data;
        int priority;
        Node<E> next;

        public Node(E data, int priority) {
            this.data = data;
            this.priority = priority;
            this.next = null;
        }
    }

    // Head of the queue
    private Node<E> head;

    // Size of the queue
    private int size;

    public PriorityQueueOrderedList() {
        head = null;
        size = 0;
    }

    /**
     * Inserts a new element into the queue with the given priority.
     *
     * @param data     Element to be inserted
     * @param priority Priority of the element
     */
    public void insert(E data, int priority) {
        Node<E> newNode = new Node<>(data, priority);

        // If queue is empty or new node has higher priority than head
        if (head == null || priority > head.priority) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<E> current = head;
            while (current.next != null && current.next.priority >= priority) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the element with the highest priority.
     *
     * @return Element with the highest priority
     * @throws NoSuchElementException if queue is empty
     */
    public E remove() {
        if (head == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        E data = head.data;
        head = head.next;
        size--;
        return data;
    }

    /**
     * Returns the element with the highest priority without removing it.
     *
     * @return Element with the highest priority
     * @throws NoSuchElementException if queue is empty
     */
    public E peek() {
        if (head == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        return head.data;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return True if queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the size of the queue.
     *
     * @return Size of the queue
     */
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        PriorityQueueOrderedList<String> pq = new PriorityQueueOrderedList<>();

        // Insert elements with priorities
        pq.insert("High Priority Task", 3);
        pq.insert("Medium Priority Task", 2);
        pq.insert("Low Priority Task", 1);

        // Remove and print elements in priority order
        while (!pq.isEmpty()) {
            System.out.println(pq.remove());
        }
    }
}