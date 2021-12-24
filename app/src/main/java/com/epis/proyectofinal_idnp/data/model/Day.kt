package com.epis.proyectofinal_idnp.data.model

class Day(_name: String, _value: Int, _color: Int){
    private var name: String = _name
    private var value: Int = _value
    private var color: Int = _color

    fun getName(): String{
        return this.name
    }

    fun getValue(): Int{
        return this.value
    }

    fun getColor(): Int{
        return this.color
    }

    override fun toString(): String {
        var msg: String = ""
        msg += "Ciudad: ${this.name} \t Valor: ${this.value} \t: ${this.color}"
        return msg
    }
}