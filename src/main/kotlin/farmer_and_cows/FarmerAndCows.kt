package com.github.fatalistix.farmer_and_cows

fun main() {
    val d = 0.5f
    val c = 3
    val N = 5
    val M = 100
    val minCowCount = 50

    val table = makeTable(N, M)

    for (j in 0..M) {
        val thisYearCows = j + minCowCount
        val nextYearCows = nextYear(thisYearCows)
        table[N-1][j] = Pair(c * (nextYearCows - minCowCount) - thisYearCows * 0.5, nextYearCows - minCowCount)
    }

    for (i in N-2 downTo 0) {
        for (j in 0..M) {
            val thisYearCows = j + minCowCount
            val nextYearCows = nextYear(thisYearCows)

            var localMax = -1_000_000.0
            var localMaxQ = Int.MIN_VALUE

            for (x in 0 .. (nextYearCows - minCowCount)) {
                if (nextYearCows - x - minCowCount > M) {
                    continue
                }

                val curr = 3 * x - 0.5 * thisYearCows + table[i + 1][nextYearCows - x - minCowCount].first
                if (curr > localMax) {
                    localMax = curr
                    localMaxQ = x
                }
            }

            table[i][j] = Pair(localMax, localMaxQ)
        }
    }

    println(table[0][0])
}

private fun nextYear(v: Int) = if (v <= 70) { v + 10 } else { v + 20 }

private fun makeTable(N: Int, M: Int): ArrayList<ArrayList<Pair<Double, Int>>> {
    val table = ArrayList<ArrayList<Pair<Double, Int>>>()

    for (i in 0 until N) {
        table.add(ArrayList(M+1))
        for (j in 0..M) {
            table[i].add(Pair(-1_000_000.0, Int.MIN_VALUE))
        }
    }

    return table
}