package com.moment.eyepetizer


class Test {
    fun breakDemo() {
        println("----- breakDemo " + "-------")
        outer@ for (outer in 1..5) {
            for (inner in 1..10) {
                println("inner = $inner")
                println("outer = $outer")
                if (inner % 2 == 0) {
                    break@outer
                }
            }
        }
    }

    fun main(args: Array<String>) {
        breakDemo()
    }
}
