package com.example.view

import tornadofx.*

class Show_sparse_terms : View("terms") {

    var l1 = label { text = "   " }

    fun show_result( print : MutableList<String>)
    {
        for ( i in print )
        {
            l1.text += i
            if ( i != "\n" ) {
                l1.text += "   "
            } else {

                l1.text += "   "
            }
        }

        this.root.add( l1 )
    }

    override val root = anchorpane {

        setPrefSize(400.0 , 300.0 )

    }

}
