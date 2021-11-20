package com.example.view

import com.example.model.SparseMatrix
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


    override val root = anchorpane {

        setPrefSize(400.0 , 300.0 )

        menu_vbox.add( label { text = "What do you want to do ?" } )
        menu_vbox.add( hbox {
            add( sum )
            add( minus )
            add( multiply )
            add( transpose )
        })
        add(menu_vbox)


    }

}
