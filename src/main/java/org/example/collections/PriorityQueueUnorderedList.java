package org.example.collections;

import java.util.NoSuchElementException;

/**
 * Priority Queue Implementation using Unordered List
 */
public class PriorityQueueUnorderedList<E> {
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

    public PriorityQueueUnorderedList() {
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
        if (head == null) {
            head = newNode;
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
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

        // Find the node with the highest priority
        Node<E> maxNode = head;
        Node<E> prevMaxNode = null;
        Node<E> current = head;
        while (current != null) {
            if (current.priority > maxNode.priority) {
                maxNode = current;
                prevMaxNode = current == head ? null : getPreviousNode(head, current);
            }
            current = current.next;
        }

        // Remove the node with the highest priority
        if (prevMaxNode == null) {
            head = maxNode.next;
        } else {
            prevMaxNode.next = maxNode.next;
        }
        size--;

        return maxNode.data;
    }

    /**
     * Returns the previous node of the given node.
     *
     * @param head Node at the head of the list
     * @param node Node whose previous node is to be found
     * @return Previous node of the given node
     */
    private Node<E> getPreviousNode(Node<E> head, Node<E> node) {
        if (head == node) {
            return null;
        }
        Node<E> current = head;
        while (current.next != null && current.next != node) {
            current = current.next;
        }
        return current;
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

        // Find the node with the highest priority
        Node<E> maxNode = head;
        Node<E> current = head;
        while (current != null) {
            if (current.priority > maxNode.priority) {
                maxNode = current;
            }
            current = current.next;
        }
        return maxNode.data;
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
        PriorityQueueUnorderedList<String> pq = new PriorityQueueUnorderedList<>();

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
