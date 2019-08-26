package com.hitales.utils

class Stack<T>(list : MutableList<T>? = null) : Iterator<T> {

    private var itCounter: Int = 0

    private var items: MutableList<T> = list?: mutableListOf()

    fun isEmpty(): Boolean = this.items.isEmpty()

    fun isNotEmpty(): Boolean = !this.items.isEmpty()

    fun count(): Int = this.items.count()

    fun size(): Int = this.items.count()

    operator fun get(i:Int):T{
       return items[i]
    }

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

    fun push(value:T) {
        this.items.add(value)
    }

    fun last():T?{
        if(items.isEmpty()){
            return null
        }
        return items.get(items.count() - 1)
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

    open fun clear(){
        items.clear()
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

    private var iterNode : Node<T>?= null

    fun isEmpty():Boolean {
        return head == null
    }

    fun first() : T? {
        return head?.value
    }

    fun last() : T? {
        return tail?.value
    }

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
            tail = head
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

    fun remove(value: T):T?{
        var temp = head
        while (temp != null){
            if(temp.value == value){
                val next = temp.next
                if(next != null){
                    temp.previous?.next = temp.next
                }else{
                    tail = temp.previous
                }
                if(temp == head){
                    head = null
                }
                return value
            }else{
                temp = temp.next
            }
        }
        return null
    }

    override fun hasNext(): Boolean {
        if(iterNode == tail){
            iterNode = null
            return false
        }
        if(iterNode == null){
            iterNode = head
        }else{
            iterNode = iterNode?.next
        }

        return iterNode != null
    }

    open fun clear(){
        var temp = head
        while (temp != null){
            temp.previous = null
            val t = temp.next
            temp.next = null
            temp = t
        }
        head = null
        tail = null
    }

    override fun next(): T = iterNode!!.value

}
