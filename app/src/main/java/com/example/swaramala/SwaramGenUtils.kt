package com.example.swaramala

import android.util.Log

class SwaramGenUtils(baseSwaramList : List<SwaramModel>) {
    var notes : List<SwaramModel> = baseSwaramList;

    fun getNextNote(prmNote : SwaramModel): SwaramModel {
        val currIndex = notes.indexOfFirst{ it.getId() === prmNote.getId() }
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

    /*
    *
    * Python functions to be converted.
    *


def getNextNSequenceForPattern(prmPattern: list, prmNumSequences):
    sequences = list([prmPattern])
    nextSequence = prmPattern
    for i in range(0, prmNumSequences - 1):
        nextSequence = getNextSequenceForPattern(nextSequence)
        sequences.append(nextSequence)
    for n in reversed(sequences):
        # print("n =", n)
        sequences.append(getMirrorImageForPattern(n))
    return sequences


def printPattern(prmPattern: list):
    for seq in prmPattern:
        for swaram in seq:
            print(swaram, end="")
            print(" ", end="    ")
        print("")


def getHighestSwaramInPattern(prmPattern: list):
    indexList = list(map(notes.index, prmPattern))
    return functools.reduce(lambda a, b: a if a > b else b, indexList)


def getLowestSwaramInPattern(prmPattern: list):
    indexList = list(map(notes.index, prmPattern))
    return functools.reduce(lambda a, b: b if a > b else a, indexList)


def getSwaramVectorFromPattern(prmPattern: list):
    vector = list()
    indexList = list(map(notes.index, prmPattern))
    prevIndex = indexList[0]
    for index in indexList:
        vector.append(index - prevIndex)
        prevIndex = index

    return vector


def getSwaramFromDelta(prmSwaram, delta):
    # print(
    #     f"Getting next swaram in sequence for {prmSwaram} with delta {delta}")
    index = notes.index(prmSwaram)
    newIndex = (index + delta) % len(notes)
    # print(notes[newIndex])
    return notes[newIndex]


def getMirrorImageForPattern(prmPattern: list):
    mirrorImage = list()
    highestSwaram = notes[getHighestSwaramInPattern(prmPattern)]
    vector = getSwaramVectorFromPattern(prmPattern)

    # take the highest swaram and apply the vector to derive  the mirror image.
    swaram = highestSwaram
    for i in range(0, len(prmPattern)):
        swaram = getSwaramFromDelta(swaram, vector[i] * -1)
        mirrorImage.append(swaram)
    # print("mirror image = ", mirrorImage)
    return mirrorImage
    * */
}