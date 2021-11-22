package com.example.view

import com.example.model.SparseMatrix
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

    val matrix1 = textfield {

    }

    val matrix2 = textfield {

    }

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

    val menu_vbox = vbox {  }

    fun resault()
    {
        if ( operator == "/" )
        {

        }
        else
        {
            val x = SparseMatrix(1,1)
            val a = x.get_input1(matrix1.text)
            val b = x.get_input2(matrix2.text)

            val n = Show_sparse_terms()
            val m = Show_sparse()

            val rel = a.add(b)
            n.show_result( rel.print() )
            println("-------------------------")
//            x.print_matrix(rel)
            m.show_result( x.print_matrix(rel) )

            m.openWindow()
            n.openWindow()
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
