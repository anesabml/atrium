package ch.tutteli.atrium.api.cc.en_GB

import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.creating.Assert

object CollectionAssertionsSpec : ch.tutteli.atrium.spec.integration.CollectionAssertionsSpec(
    AssertionVerbFactory,
    Assert<Collection<Int>>::hasSize.name to Assert<Collection<Int>>::hasSize,
    Assert<Collection<Int>>::isEmpty.name to Assert<Collection<Int>>::isEmpty,
    Assert<Collection<Int>>::isNotEmpty.name to Assert<Collection<Int>>::isNotEmpty
)
