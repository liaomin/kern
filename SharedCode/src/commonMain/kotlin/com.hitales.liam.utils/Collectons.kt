package com.hitales.liam.utils

class Stack<T>(list : MutableList<T>? = null) : Iterator<T> {

    var itCounter: Int = 0

    var items: MutableList<T> = list?: mutableListOf()

    fun isEmpty(): Boolean = this.items.isEmpty()

    fun count(): Int = this.items.count()

    override fun toString(): String {
        return this.items.toString()
    }

    fun pop(): T? {
        if (this.isEmpty()) {
            return null
        } else {
            val item = this.items.count() - 1
            return this.items.removeAt(item)
        }
    }

    fun peek(): T? {
        if (isEmpty()) {
            return null
        } else {
            return this.items[this.items.count() - 1]
        }
    }

    fun append(value:T) {
        this.items.add(value)
    }

    override fun hasNext(): Boolean {
        val hasNext = itCounter < count()
        if (!hasNext) itCounter = 0
        return hasNext
    }

    override fun next(): T {
        if (hasNext()){
            val topPos : Int = (count() - 1) - itCounter
            itCounter++
            return this.items[topPos]
        }else{
            throw NoSuchElementException("No such element") // 异常不用new哦
        }
    }
}

class LinkedList<T> : Iterator<T>{

    class Node<T>(value : T){
        var value : T = value
        var next : Node<T>? = null
        var previous : Node<T>? = null
    }

    var head : Node<T>?= null

    var tail : Node<T>?= head

    var iterNode : Node<T>?= null

    var isEmpty : Boolean = head == null

    fun first() : T? = head?.value

    fun last() : T? = tail?.value

    fun count():Int {
        var node = head
        if (node != null){
            var counter = 1
            while (node?.next != null){
                node = node?.next
                counter += 1
            }
            return counter
        } else {
            return 0
        }
    }

    fun append(value : T){
        var newNode = Node(value)
        if (tail != null){
            newNode.previous = tail
            tail?.next = newNode
        }else{
            head = newNode
        }
    }

    fun pop():T?{
        if(tail != null){
            val temp = tail?.value
            tail = tail?.previous
            return temp
        }
        return null
    }

    override fun hasNext(): Boolean {
        if(iterNode == null){
            iterNode = head
        }
        if(iterNode == tail){
            iterNode = null
            return false
        }
        return iterNode?.next != null
    }

    override fun next(): T = iterNode!!.next!!.value

}
