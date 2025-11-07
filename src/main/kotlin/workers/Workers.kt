package com.github.fatalistix.workers

fun main() {
    val N = 5
    val M = 10
    val a = generateA(N)

    val table = createTable(N, M)

    for (j in 0..M) {
        table[N-1][j] = if (j < a[N-1]) {
            Pair(4 + (a[N-1] - j) * 2, a[N-1] - j)
        } else if (j == a[N-1]) {
            Pair(0, 0)
        } else {
            Pair((j - a[N-1]) * 2, a[N-1] - j)
        }
    }

    for (i in N-2 downTo 0) {
        for (j in 0..M) {
            var localMin = Int.MAX_VALUE
            var localMinQ = Int.MAX_VALUE

            if (j < a[i]) {
                for (x in a[i]..M) {
                    val q = x - j

                    val curr = 4 + 2 * q + 3 * (x - a[i]) + table[i + 1][x].first
                    if (curr < localMin) {
                        localMin = curr
                        localMinQ = q
                    }
                }
            } else if (j == a[i]) {
                for (x in (a[i] + 1)..M) {
                    val q = x - j

                    val curr = 4 + 2 * q + 3 * (x - a[i]) + table[i + 1][x].first
                    if (curr < localMin) {
                        localMin = curr
                        localMinQ = q
                    }
                }

                val curr = 3 * (j - a[i]) + table[i + 1][j].first
                if (curr < localMin) {
                    localMin = curr
                    localMinQ = 0
                }
            } else {
                for (x in a[i] until j) {
                    val q = x - j

                    val curr = -2 * q + 3 * (x - a[i]) + table[i + 1][x].first
                    if (curr < localMin) {
                        localMin = curr
                        localMinQ = q
                    }
                }

                for (x in (j+1)..M) {
                    val q = x - j

                    val curr = 4 + 2 * q + 3 * (x - a[i]) + table[i + 1][x].first
                    if (curr < localMin) {
                        localMin = curr
                        localMinQ = q
                    }
                }

                val curr = 3 * (j - a[i]) + table[i + 1][j].first
                if (curr < localMin) {
                    localMin = curr
                    localMinQ = 0
                }
            }

            table[i][j] = Pair(localMin, localMinQ)
        }
    }

    println(table[0][0])
}

fun generateA(N: Int): List<Int> {
    val result = ArrayList<Int>()

    result.add(5)
    result.add(7)
    result.add(8)
    result.add(4)
    result.add(6)

    require(N == result.size)
    return result
}

fun createTable(N: Int, M: Int): ArrayList<ArrayList<Pair<Int, Int>>> {
    val table = ArrayList<ArrayList<Pair<Int, Int>>>()

    for (i in 0 until N) {
        table.add(ArrayList())

        for (j in 0..M) {
            table[i].add(Pair(Int.MAX_VALUE, Int.MIN_VALUE))
        }
    }

    return table
}