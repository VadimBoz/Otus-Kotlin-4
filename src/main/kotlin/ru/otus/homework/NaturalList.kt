package ru.otus.homework

/**
 * Список натуральных чисел от 1 до n
 * @param n Последнее натуральное число в списке
 */
class NaturalList(n: Int) : List<Int> {
    override val size: Int = n

    override fun get(index: Int): Int = if (index in 0 until size) {
        index + 1
    } else {
        throw NoSuchElementException("No such index $index. Total elements: $size")
    }

    override fun isEmpty(): Boolean = 0 == size

    override fun iterator(): Iterator<Int> = NaturalIterator(size)

    override fun listIterator(): ListIterator<Int> = NaturalListIterator(size)

    override fun listIterator(index: Int): ListIterator<Int> = NaturalListIterator(size, index)

    override fun contains(element: Int): Boolean = element in 1..size

    override fun indexOf(element: Int): Int = if (contains(element)) {
        element - 1
    } else {
        -1
    }

    override fun lastIndexOf(element: Int): Int = indexOf(element)

    /**
     * Вернуть под-список этого списка, включая [fromIndex] и НЕ включая [toIndex]
     */
    override fun subList(fromIndex: Int, toIndex: Int): List<Int> {
        return filterIndexed { index, _ -> index in fromIndex until toIndex }
    }

    /**
     * Returns true if list contains all numbers in the collection
     */
    override fun containsAll(elements: Collection<Int>): Boolean {
        return elements.all { this.contains(it) }
    }

    override fun toString(): String {
        return joinToString(prefix = "[", postfix = "]", separator = ", ")
    }

    /**
     * Функция должна возвращать true, если сравнивается с другой реализацией списка тех же чисел
     * Например, NaturalList(5) должен быть равен listOf(1,2,3,4,5)
     */
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other is List<*> && (other as List<*>)[0] is Int && (other as List<*>).size == size) {
            containsAll(other as List<*>)
            return all { (other as List<*>).contains(it) }
        }
        return false
    }

    private fun naturalList() = this

    /**
     * Функция должна возвращать тот же hash-code, что и список другой реализации тех же чисел
     * Например, NaturalList(5).hashCode() должен быть равен listOf(1,2,3,4,5).hashCode()
     */
    override fun hashCode(): Int {
        var hashCode = 1
        for (i in this) {
            hashCode = 31 * hashCode + (i.hashCode())
        }
        return hashCode
    }
}

private class NaturalIterator(private val n: Int) : Iterator<Int> {
    private var index = 0
    override fun hasNext(): Boolean = index < n
    override fun next(): Int = if (hasNext()) {
        ++index
    } else {
        throw NoSuchElementException()
    }
}

private class NaturalListIterator(private val n: Int, index: Int = 0) : ListIterator<Int> {
    private var index: Int = index.coerceIn(0, n - 1)
    override fun hasNext(): Boolean = index < n
    override fun hasPrevious(): Boolean = index > 0
    override fun next(): Int = if (hasNext()) {
        ++index
    } else {
        throw NoSuchElementException()
    }

    override fun nextIndex(): Int = index
    override fun previous(): Int = if (hasPrevious()) {
        index--
    } else {
        throw NoSuchElementException()
    }

    override fun previousIndex(): Int = index
}