package com.example.swaramala

class SwaramModel(private var id : String,private var name : String, private var label : String) {
    fun getId(): String {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getLabel(): String {
        return label
    }

    fun setLabel(label: String) {
        this.label = label
    }
}