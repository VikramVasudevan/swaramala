package com.example.swaramala

import android.util.Log

class SwaramGenUtils(baseSwaramList : List<SwaramModel>) {
    var notes : List<SwaramModel> = baseSwaramList;

    fun getIndexOfNote (prmNote : SwaramModel): Int {
        val currIndex = notes.indexOfFirst{ it.getId() === prmNote.getId() }
        return currIndex
    }
    fun getNextNote(prmNote : SwaramModel): SwaramModel {
        val currIndex = getIndexOfNote(prmNote)
        Log.i("SwaramGenUtils","currIndex = $currIndex")
        val nextIndex = (currIndex + 1) % notes.size
        return notes.get(nextIndex)

    }

    fun getNextNNotes(prmNote : SwaramModel, prmNumSwarams : Int): List<SwaramModel> {
        var nextNotes = ArrayList<SwaramModel>()

        var nextNote = prmNote
        for(i in 1..prmNumSwarams) {
            nextNote = getNextNote(nextNote)
            nextNotes.add(nextNote)
        }
        return nextNotes
    }

    fun getNextSequenceForPattern(prmPattern: List<SwaramModel>): List<SwaramModel> {
        return prmPattern.map { getNextNote(it)}
    }

    fun getNextNSequenceForPattern(prmPattern: List<SwaramModel>, prmNumSequences : Int): ArrayList<List<SwaramModel>> {
        var sequences : ArrayList<List<SwaramModel>> = ArrayList(listOf(prmPattern))
        var nextSequence = prmPattern
        for( i in 0..prmNumSequences - 1) {
            nextSequence = getNextSequenceForPattern(nextSequence)
            sequences.add(nextSequence)
        }
        for(n in sequences.reversed()) {
            sequences.add(getMirrorImageForPattern(n))
         }
        return sequences
    }

    fun findMaxNumber(numbers: List<Int>): Int? {
        if (numbers.isEmpty()) return null

        var maxNumber = numbers[0]
        for (number in numbers) {
            if (number > maxNumber) {
                maxNumber = number
            }
        }
        return maxNumber
    }

    fun findMinNumber(numbers: List<Int>): Int? {
        if (numbers.isEmpty()) return null

        var minNumber = numbers[0]
        for (number in numbers) {
            if (number < minNumber) {
                minNumber = number
            }
        }
        return minNumber
    }

    fun getHighestSwaramInPattern(prmPattern: List<SwaramModel>): Int? {
        val indexList = prmPattern.map{ getIndexOfNote(it) }

        return findMaxNumber(indexList)
    }

    fun getLowestSwaramInPattern(prmPattern: List<SwaramModel>): Int? {
        val indexList = prmPattern.map{ getIndexOfNote(it) }

        return findMinNumber(indexList)
    }

    fun getSwaramVectorFromPattern(prmPattern: List<SwaramModel>): List<Int> {
        var vector = ArrayList<Int>()
        val indexList = prmPattern.map{ getIndexOfNote(it) }
        var prevIndex = indexList.get(0)
        for(index in indexList) {
            vector.add(index - prevIndex)
            prevIndex = index
        }
        return vector
    }

    fun getSwaramFromDelta(prmSwaram : SwaramModel, delta : Int): SwaramModel {
        val index = getIndexOfNote(prmSwaram)
        val newIndex = (index + delta) % notes.size
        return notes.get(newIndex)
    }

    fun getMirrorImageForPattern(prmPattern: List<SwaramModel>):List<SwaramModel> {
        var mirrorImage = ArrayList<SwaramModel>();
        val highestSwaram = getHighestSwaramInPattern(prmPattern)?.let { notes.get(it) }
        val vector = getSwaramVectorFromPattern(prmPattern)

        // take the highest swaram and apply the vector to derive the mirror image.
        var swaram =         highestSwaram
        for(i in 0..prmPattern.size-1) {
            val swaram = swaram?.let { getSwaramFromDelta(it, vector[i] * -1) }
            if (swaram != null) {
                mirrorImage.add(swaram)
            }
            // print("mirror image = ", mirrorImage)
        }
        return mirrorImage
    }

//    fun printPattern(prmPattern: List<SwaramModel>) {
//        for (seq in prmPattern) {
//            for (swaram in seq) {
//                print(swaram, end = "")
//                print(" ", end = "    ")
//                print("")
//            }
//        }
//        }
//    }
}