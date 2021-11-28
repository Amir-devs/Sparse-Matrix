package com.example.view

import javafx.scene.control.ScrollBar
import tornadofx.*

class Show_sparse_terms : View("terms") {


    var l1 = label {
        text = "    "
        style = "-fx-font-size: 15px"
    }

    fun show_result( print : MutableList<String>)
    {
        for ( i in print )
        {
            l1.text += i
            if ( i != "\n" )
            {
                l1.text += "   "
            }
            else if ( i.length > 1 )
            {
                l1.text += "   "
            } else
            {
                l1.text += "    "
            }

        }

        this.root.add( l1 )
    }

    override val root = anchorpane {

    }

}
