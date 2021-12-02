package com.example.model

import java.lang.Exception

class SparseMatrix(
    var row: Int,
    var col: Int
) {

    // result data ( terms )
    val result_list = mutableListOf<String>()

    // result data ( sparse )
    val result_list2 = mutableListOf<String>()


    private var data = Array(100 ) { IntArray(3) }
    // useful length of data ( terms )
    private var len = 0

    var row_size = 0
    var column_size = 0

    fun addData(r: Int, c: Int, `val`: Int) {

        data[len][0] = r
        data[len][1] = c
        data[len++][2] = `val`
    }

    fun add(b: SparseMatrix): SparseMatrix {
        var a_index = 0
        var b_index = 0
        val temp = SparseMatrix(row, col)
        while (a_index < len && b_index < b.len) { // when none is empty, scan both matrix
            if (data[a_index][0] > b.data[b_index][0] || data[a_index][0] == b.data[b_index][0] && data[a_index][1] > b.data[b_index][1]) { // b smaller
                // copy b to new matrix
                temp.addData(b.data[b_index][0], b.data[b_index][1], b.data[b_index][2])
                b_index++
            } else if (data[a_index][0] < b.data[b_index][0] || data[a_index][0] == b.data[b_index][0] && data[a_index][1] < b.data[b_index][1]) { // a smaller
                // copy a to new matrix
                temp.addData(data[a_index][0], data[a_index][1], data[a_index][2])
                a_index++
            } else { // data at aPos and bPos are the same row and column
                val rel = data[a_index][2] + b.data[b_index][2]
                if (rel != 0) temp.addData(data[a_index][0], data[a_index][1], rel)
                a_index++
                b_index++
            }
        }
        // copy remain data
        while (a_index < len)
        {
            temp.addData(data[a_index][0], data[a_index][1], data[a_index++][2])
        }
        while (b_index < b.len)
        {
            temp.addData(b.data[b_index][0], b.data[b_index][1], b.data[b_index++][2])
        }
        return temp
    }

    fun minus(b: SparseMatrix): SparseMatrix {
        var a_index = 0
        var b_index = 0
        val temp = SparseMatrix(row, col)
        while (a_index < len && b_index < b.len) { // when none is empty, scan both matrix
            if (data[a_index][0] > b.data[b_index][0] || data[a_index][0] == b.data[b_index][0] && data[a_index][1] > b.data[b_index][1]) { // b smaller
                // copy b to new matrix
                temp.addData(b.data[b_index][0], b.data[b_index][1], b.data[b_index][2])
                b_index++
            } else if (data[a_index][0] < b.data[b_index][0] || data[a_index][0] == b.data[b_index][0] && data[a_index][1] < b.data[b_index][1]) { // a smaller
                // copy a to new matrix
                temp.addData(data[a_index][0], data[a_index][1], data[a_index][2])
                a_index++
            } else { // data at aPos and bPos are the same row and column
                val rel = data[a_index][2] - b.data[b_index][2]
                if (rel != 0) temp.addData(data[a_index][0], data[a_index][1], rel)
                a_index++
                b_index++
            }
        }
        // copy remain data
        while (a_index < len)
        {
            temp.addData(data[a_index][0], data[a_index][1], data[a_index++][2])
        }
        while (b_index < b.len)
        {
            temp.addData(b.data[b_index][0], b.data[b_index][1], b.data[b_index++][2])
        }
        return temp
    }

    fun transpose(): SparseMatrix {
        val temp = SparseMatrix(col, row)
        temp.data = Array(len) { IntArray(3) }
        temp.len = len
        temp.row = col
        temp.col = row
        val count = IntArray(col) // count[i]: how many data in column i
        for (i in 0 until len)
        {
            count[data[i][1]]++
        }
        val index = IntArray(col) // index[i]: how many data have column smaller than i
        for (i in 1 until col)
        {
            index[i] = index[i - 1] + count[i - 1]
        }
        for (i in 0 until len)
        {
            val insertPos = index[data[i][1]]++ // a new data inserted
            temp.data[insertPos][0] = data[i][1] // transpose
            temp.data[insertPos][1] = data[i][0] // transpose
            temp.data[insertPos][2] = data[i][2] // copy data
        }
        return temp
    }

    fun multiply(x: SparseMatrix): SparseMatrix {

        val b = x.transpose()
        eror_handler( col , b.col )

        var a_index : Int
        var b_index : Int
        val rel = SparseMatrix(row, b.row)
        a_index = 0
        while (a_index < len) {
            val r = data[a_index][0] // current row
            b_index = 0
            while (b_index < b.len) {
                val c = b.data[b_index][0] // current column
                var scanA = a_index
                var scanB = b_index
                var sum = 0
                while (scanA < len && data[scanA][0] == r && scanB < b.len && b.data[scanB][0] == c) { // calculate rel[r][c]
                    if (data[scanA][1] < b.data[scanB][1]) // scanB has larger column
                        scanA++ // skip a
                    else if (data[scanA][1] > b.data[scanB][1]) // scanA has larger column
                        scanB++ // skip b
                    else  // same column, can multiply
                        sum += data[scanA++][2] * b.data[scanB++][2]
                }
                if (sum != 0) rel.addData(r, c, sum)
                while (b_index < b.len && b.data[b_index][0] == c) b_index++ // jump to next column
            }
            while (a_index < len && data[a_index][0] == r) a_index++ // jump to next row
        }
        return rel
    }

    fun eror_handler(x : Int , y : Int)
    {
        if ( x != y )
        {
            println("first matrix column not match to second matrix row !!")
            System.exit(0)
        }

    }

    fun get_input1(data : String): SparseMatrix
    {
        val row = data.split("/")
        val column = row[0].split(" ")

        val array1 = Array(row.size) { Array(column.size - 1) { 0 } }

        for (i in row.indices)
        {
            val col = row[i].trim().split("\\s+".toRegex())
            for (j in col.indices)
            {
                if (col[j] != "0")
                {
                    array1[i][j] = col[j].toInt()
                }
            }
        }

        val a = SparseMatrix( row.size , column.size - 1 )
        for (i in 0 until row.size)
        {
            for (j in 0 until column.size - 1)
            {
                if ( array1[i][j] != 0 )
                {
                    a.addData(i,j,array1[i][j])
                }
            }
        }

        row_size = row.size
        column_size = column.size - 2

        return a
    }

    fun get_input2(data2 : String): SparseMatrix
    {
        val row2 = data2.split("/")
        val column2 = row2[0].split(" ")

        var array2 = Array(row2.size) { Array(column2.size - 1) { 0 } }

        for (i in row2.indices)
        {
            val col = row2[i].trim().split("\\s+".toRegex())
            for (j in col.indices)
            {
                if (col[j] != "0")
                {
                    array2[i][j] = col[j].toInt()
                }
            }
        }

        val b = SparseMatrix( row2.size , column2.size-1 )
        for (i in 0 until row2.size)
        {
            for (j in 0 until column2.size - 1)
            {
                if ( array2[i][j] != 0 )
                {
                    b.addData(i,j,array2[i][j])
                }
            }
        }

        return b
    }

    fun print(): MutableList<String> {

        println("row = $row , column = $col")
        for (i in 0 until len) {
            print(data[i][0])
            print(" ")
            result_list.add(data[i][0].toString())
            print(data[i][1])
            print(" ")
            result_list.add(data[i][1].toString())
            print(data[i][2])
            println()
            result_list.add(data[i][2].toString())
            result_list.add("\n")
        }

        return result_list
    }

    fun print_matrix(rel : SparseMatrix): MutableList<String> {
        var temp = 0
        for ( i in 0..row_size-1 )
        {
            for ( j in 0..column_size )
            {
                if ( rel.data[temp][0] == i && rel.data[temp][1] == j )
                {
                    print( rel.data[temp][2] )
                    result_list2.add( rel.data[temp][2].toString() )
                    temp ++
                    print(" , ")
                }
                else
                {
                    print(0)
                    result_list2.add( "0" )
                    print(" , ")
                }

//                if ( temp == rel.len )
//                {
//                    break
//                }
            }

            result_list2.add( "\n" )
//            if ( temp == rel.len )
//            {
//                break
//            }
            println()
        }

        return result_list2
    }

    fun print_matrix_transpose(rel : SparseMatrix): MutableList<String> {
        var temp = 0
        println("row = $row , column = $col")
        try {
            for ( i in 0..row-1 )
            {
                for ( j in 0..col-1 )
                {
                    if ( rel.data[temp][0] == i && rel.data[temp][1] == j )
                    {
                        print( rel.data[temp][2] )
                        result_list2.add( rel.data[temp][2].toString() )
                        temp ++
                        print(" , ")
                    }
                    else
                    {
                        print(0)
                        result_list2.add( "0" )
                        print(" , ")
                    }
                }

                result_list2.add( "\n" )
                println()
            }

        } catch ( e : Exception )
        {

        }

            result_list2.add( "\n" )

            println()

        return result_list2
    }


    companion object {
        @JvmStatic
        fun main(argv: Array<String>) { // for unit test

//            val x = SparseMatrix(1,1)
//            val a = x.get_input1()
//            val b = x.get_input2()
//

//            val rel = a.minus(b)
//            rel.print()
//            println("-------------------------")
//            x.print_matrix(rel)

            // 4 9 0 / 4 6 2
            // 0 3 0 / 0 0 9
        }
    }


}