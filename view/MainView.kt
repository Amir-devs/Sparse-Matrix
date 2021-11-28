package com.example.view

import com.example.model.SparseMatrix
import javafx.scene.control.ScrollBar
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox
import tornadofx.*

class MainView : View("Hello TornadoFX") {

    // operator
    var operator = ""

    val sum = button {
        text = "Sum"
        action { operator = "+" ; show_field() }
    }

    val minus = button {
        text = "Minus"
        action { operator = "-" ; show_field()}
    }

    val multiply = button {
        text = "Multiply"
        action { operator = "*" ; show_field()}
    }

    val transpose = button {
        text = "Transpose"
        action { operator = "/" ; show_field()}
    }

    val calculate = button {
        text = "Calculate"
        action { resault() }
    }

    val matrix1 = textfield { }

    val matrix2 = textfield { }

    fun show_field()
    {
        if ( operator == "/" )
        {
            this.menu_vbox.add(label { text = "matrix 1 : " })
            this.menu_vbox.add(matrix1)
            this.menu_vbox.add(calculate)
        }
        else
        {
            this.menu_vbox.add(label { text = "matrix 1 : " })
            this.menu_vbox.add(matrix1)
            this.menu_vbox.add(label { text = "matrix 2 : " })
            this.menu_vbox.add(matrix2)
            this.menu_vbox.add(calculate)
        }

    }

    val menu_vbox = vbox { }

    fun show_add()
    {
        val x = SparseMatrix(1,1)
        val a = x.get_input1(matrix1.text)
        val b = x.get_input2(matrix2.text)

        val n = Show_sparse_terms()
        val m = Show_sparse()

        val rel = a.add(b)
        n.show_result( rel.print() )
        println("-------------------------")
        m.show_result( x.print_matrix(rel) )

        m.openWindow()
        n.openWindow()
    }

    fun show_minus()
    {
        val x = SparseMatrix(1,1)
        val a = x.get_input1(matrix1.text)
        val b = x.get_input2(matrix2.text)

        val n = Show_sparse_terms()
        val m = Show_sparse()

        val rel = a.minus(b)
        n.show_result( rel.print() )
        println("-------------------------")
        m.show_result( x.print_matrix(rel) )

        m.openWindow()
        n.openWindow()
    }

    fun show_multiply()
    {
        val x = SparseMatrix(1,1)
        val a = x.get_input1(matrix1.text)
        val b = x.get_input2(matrix2.text)

        val n = Show_sparse_terms()
        val m = Show_sparse()

        val rel = a.multiply(b)
        n.show_result( rel.print() )
        println("-------------------------")
        m.show_result( x.print_matrix(rel) )

        m.openWindow()
        n.openWindow()
    }

    fun show_transpose()
    {
        val x = SparseMatrix(1,1)
        val a = x.get_input1(matrix1.text)

        val n = Show_sparse_terms()
        val m = Show_sparse()

        val rel = a.transpose()
        n.show_result( rel.print() )
        println("-------------------------")
        m.show_result( rel.print_matrix_transpose(rel) )

        m.openWindow()
        n.openWindow()
    }


    fun resault()
    {
        when ( operator )
        {
            "+" -> show_add()
            "-" -> show_minus()
            "*" -> show_multiply()
            "/" -> show_transpose()
        }
    }

    fun add_menu(): VBox {
        menu_vbox.add( label { text = "What do you want to do ?" } )
        menu_vbox.add( hbox {
            add( sum )
            add( minus )
            add( multiply )
            add( transpose )
        })

        return menu_vbox
    }

    override val root = anchorpane {

        setPrefSize(400.0 , 300.0 )
        add( add_menu() )



    }



}
