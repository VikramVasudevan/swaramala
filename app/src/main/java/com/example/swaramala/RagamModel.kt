package com.example.swaramala

class RagamModel(private var id : String, private var name : String, private var label : String, private var swarams : List<SwaramModel>) {
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

    fun getSwarams(): List<SwaramModel> {
        return swarams
    }

    fun setSwarams(swarams: List<SwaramModel>) {
        this.swarams = swarams
    }

    override fun toString(): String {
        return label
    }
}