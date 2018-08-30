package ch.tutteli.atrium.api.cc.infix.en_GB

import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.api.cc.infix.en_GB.keywords.Empty
import ch.tutteli.atrium.creating.Assert

class CollectionAssertionsSpec : ch.tutteli.atrium.spec.integration.CollectionAssertionsSpec(
    AssertionVerbFactory,
    Assert<List<Int>>::hasSize.name to Companion::hasSize,
    "${Assert<List<Int>>::toBe.name} ${Empty::class.simpleName}" to Companion::isEmpty,
    "${Assert<List<Int>>::notToBe.name} ${Empty::class.simpleName}" to Companion::isNotEmpty
) {
    companion object {
        private fun hasSize(plant: Assert<List<Int>>, size: Int): Assert<List<Int>>
            = plant hasSize size

        private fun isEmpty(plant: Assert<List<Int>>)
            = plant toBe Empty

        private fun isNotEmpty(plant: Assert<List<Int>>)
            = plant notToBe Empty
    }
}
