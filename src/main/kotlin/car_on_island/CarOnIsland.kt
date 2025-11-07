package com.github.fatalistix.car_on_island

import kotlin.math.min

fun main() {
    val M = 45
    val N = 5
    val m = generateM(N)
    val t = generateT(N)

    val table = createTable(M, N)

    for (j in 0..M) {
        val toTake = j / m[N-1]
        val timeToWork = toTake * t[N-1]
        table[N-1][j] = Pair(timeToWork, toTake)
    }

    for (i in N-2 downTo 0) {
        for (j in 0..M) {
            var localMax = Int.MIN_VALUE
            var toTakeForMax = Int.MIN_VALUE

            for (x in 0..j) {
                val toTake = x / m[i]
                val timeToWork = toTake * t[i]

                val left = table[i+1][j-x]
                val result = min(timeToWork, left.first)
                if (result > localMax) {
                    localMax = result
                    toTakeForMax = toTake
                }
            }
            table[i][j] = Pair(localMax, toTakeForMax)
        }
    }

    var p = M
    val resultArr = ArrayList<Int>(N)
    for (i in 0 until N) {
        resultArr.add(table[i][p].second)
        p -= resultArr[i] * m[i]
    }

    println(resultArr)
    println(table[0][M].first)
}

private fun generateM(N: Int): List<Int> {
    val l = ArrayList<Int>()
    l.add(4)
    l.add(2)
    l.add(3)
    l.add(6)
    l.add(5)

    require(l.size == N)

    return l
}

private fun generateT(N: Int): List<Int> {
    val l = ArrayList<Int>()
    l.add(7)
    l.add(8)
    l.add(4)
    l.add(5)
    l.add(5)

    require(l.size == N)

    return l
}

private fun createTable(M: Int, N: Int): ArrayList<ArrayList<Pair<Int, Int>>> {
    val table = ArrayList<ArrayList<Pair<Int, Int>>>()

    for (i in 0 until N) {
        val column = ArrayList<Pair<Int, Int>>()
        for (j in 0..M) {
            column.add(Pair(Int.MIN_VALUE, Int.MIN_VALUE))
        }
        table.add(column)
    }

    return table
}