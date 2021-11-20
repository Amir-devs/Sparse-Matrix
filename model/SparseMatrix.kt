package com.example.model

class SparseMatrix(
    private var row: Int, // size of the matrix
    private var col: Int
) {

    private var data = Array(10) { IntArray(3) }
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
        var aPos = 0
        var bPos = 0
        val tmp = SparseMatrix(row, col)
        while (aPos < len && bPos < b.len) { // when none is empty, scan both matrix
            if (data[aPos][0] > b.data[bPos][0] || data[aPos][0] == b.data[bPos][0] && data[aPos][1] > b.data[bPos][1]) { // b smaller
                // copy b to new matrix
                tmp.addData(b.data[bPos][0], b.data[bPos][1], b.data[bPos][2])
                bPos++
            } else if (data[aPos][0] < b.data[bPos][0] || data[aPos][0] == b.data[bPos][0] && data[aPos][1] < b.data[bPos][1]) { // a smaller
                // copy a to new matrix
                tmp.addData(data[aPos][0], data[aPos][1], data[aPos][2])
                aPos++
            } else { // data at aPos and bPos are the same row and column
                val rel = data[aPos][2] + b.data[bPos][2]
                if (rel != 0) tmp.addData(data[aPos][0], data[aPos][1], rel)
                aPos++
                bPos++
            }
        }
        // copy remain data
        while (aPos < len)
        {
            tmp.addData(data[aPos][0], data[aPos][1], data[aPos++][2])
        }
        while (bPos < b.len)
        {
            tmp.addData(b.data[bPos][0], b.data[bPos][1], b.data[bPos++][2])
        }
        return tmp
    }

    fun minus(b: SparseMatrix): SparseMatrix {
        var aPos = 0
        var bPos = 0
        val tmp = SparseMatrix(row, col)
        while (aPos < len && bPos < b.len) { // when none is empty, scan both matrix
            if (data[aPos][0] > b.data[bPos][0] || data[aPos][0] == b.data[bPos][0] && data[aPos][1] > b.data[bPos][1]) { // b smaller
                // copy b to new matrix
                tmp.addData(b.data[bPos][0], b.data[bPos][1], b.data[bPos][2])
                bPos++
            } else if (data[aPos][0] < b.data[bPos][0] || data[aPos][0] == b.data[bPos][0] && data[aPos][1] < b.data[bPos][1]) { // a smaller
                // copy a to new matrix
                tmp.addData(data[aPos][0], data[aPos][1], data[aPos][2])
                aPos++
            } else { // data at aPos and bPos are the same row and column
                val rel = data[aPos][2] - b.data[bPos][2]
                if (rel != 0) tmp.addData(data[aPos][0], data[aPos][1], rel)
                aPos++
                bPos++
            }
        }
        // copy remain data
        while (aPos < len)
        {
            tmp.addData(data[aPos][0], data[aPos][1], data[aPos++][2])
        }
        while (bPos < b.len)
        {
            tmp.addData(b.data[bPos][0], b.data[bPos][1], b.data[bPos++][2])
        }
        return tmp
    }

    fun transpose(): SparseMatrix {
        val tmp = SparseMatrix(col, row)
        tmp.data = Array(len) { IntArray(3) }
        tmp.len = len
        tmp.row = col
        tmp.col = row
        val count = IntArray(col) // count[i]: how many data in column i
        for (i in 0 until len) count[data[i][1]]++
        val index = IntArray(col) // index[i]: how many data have column smaller than i
        for (i in 1 until col) index[i] = index[i - 1] + count[i - 1]
        for (i in 0 until len) {
            val insertPos = index[data[i][1]]++ // a new data inserted, so shift insertion point
            tmp.data[insertPos][0] = data[i][1] // transpose
            tmp.data[insertPos][1] = data[i][0] // transpose
            tmp.data[insertPos][2] = data[i][2] // copy data
        }
        return tmp
    }

    fun multiply(x: SparseMatrix): SparseMatrix {
        val b = x.transpose()
        var aPos: Int
        var bPos: Int
        val rel = SparseMatrix(row, b.row)
        aPos = 0
        while (aPos < len) {
            val r = data[aPos][0] // current row
            bPos = 0
            while (bPos < b.len) {
                val c = b.data[bPos][0] // current column
                var scanA = aPos
                var scanB = bPos
                var sum = 0
                while (scanA < len && data[scanA][0] == r && scanB < b.len && b.data[scanB][0] == c) { // calculate rel[r][c]
                    if (data[scanA][1] < b.data[scanB][1]) // scanB has larger column
                        scanA++ // skip a
                    else if (data[scanA][1] > b.data[scanB][1]) // scanA has larger column
                        scanB++ // skip b
                    else  // same column, so they can multiply
                        sum += data[scanA++][2] * b.data[scanB++][2]
                }
                if (sum != 0) rel.addData(r, c, sum)
                while (bPos < b.len && b.data[bPos][0] == c) bPos++ // jump to next column
            }
            while (aPos < len && data[aPos][0] == r) aPos++ // jump to next row
        }
        return rel
    }

    fun print() {
        println("row = $row , column = $col")
        for (i in 0 until len) {
            print(data[i][0])
            print(" ")
            print(data[i][1])
            print(" ")
            print(data[i][2])
            println()
        }
    }

    fun get_input1(): SparseMatrix
    {
        println("enter matrix : ")
        val data: String = readLine().toString()

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

    fun get_input2(): SparseMatrix
    {
        println("enter matrix : ")
        val data2: String = readLine().toString()

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


    fun print_matrix(rel : SparseMatrix)
    {
        var temp = 0
        for ( i in 0..row_size )
        {
            for ( j in 0..column_size )
            {
                if ( rel.data[temp][0] == i && rel.data[temp][1] == j )
                {
                    print( rel.data[temp][2] )
                    temp ++
                    print(" , ")
                }
                else
                {
                    print(0)
                    print(" , ")
                }

                if ( temp == rel.len )
                {
                    break
                }
            }

            if ( temp == rel.len )
            {
                break
            }
            println()
        }
    }



    companion object {
        @JvmStatic
        fun main(argv: Array<String>) { // for unit test

            val x = SparseMatrix(1,1)
            val a = x.get_input1()
            val b = x.get_input2()

            val rel = a.minus(b)
            rel.print()
            println("-------------------------")
            x.print_matrix(rel)

            // 4 9 0 / 4 6 2
            // 0 3 0 / 0 0 9
        }
    }


}