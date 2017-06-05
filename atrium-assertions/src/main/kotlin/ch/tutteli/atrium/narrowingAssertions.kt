package ch.tutteli.atrium

import ch.tutteli.atrium.DescriptionNarrowingAssertion.IS_A
import ch.tutteli.atrium.DescriptionNarrowingAssertion.IS_NOT_NULL
import ch.tutteli.atrium.assertions.IAssertion
import ch.tutteli.atrium.creating.IAssertionPlant
import ch.tutteli.atrium.creating.IAssertionPlantNullable
import ch.tutteli.atrium.creating.IAssertionPlantWithCommonFields
import ch.tutteli.atrium.creating.createAssertionsAndCheckThem
import ch.tutteli.atrium.reporting.translating.IEnTranslatable
import ch.tutteli.atrium.reporting.translating.Untranslatable
import kotlin.reflect.KProperty0

/**
 * Makes the assertion that [IAssertionPlant.subject] is not null.
 *
 * @return This plant to support a fluent-style API.
 */
inline fun <reified T : Any> IAssertionPlantNullable<T?>.isNotNull()
    = AtriumFactory.newDownCastBuilder(IS_NOT_NULL, commonFields)
    .cast()

/**
 * Makes the assertion that [IAssertionPlant.subject] is not null and if so, uses [createAssertions]
 * which could create further assertions which are lazily evaluated at the end.
 *
 * @return This plant to support a fluent-style API.
 */
inline fun <reified T : Any> IAssertionPlantNullable<T?>.isNotNull(noinline createAssertions: IAssertionPlant<T>.() -> Unit)
    = AtriumFactory.newDownCastBuilder(IS_NOT_NULL, commonFields)
    .withLazyAssertions(createAssertions)
    .cast()

/**
 * Makes the assertion that [IAssertionPlant.subject] *is a* [TSub] (the same type or a sub-type).
 *
 * @return This plant to support a fluent-style API.
 */
inline fun <reified TSub : Any> IAssertionPlant<Any>.isA(): IAssertionPlant<TSub>
    = AtriumFactory.newDownCastBuilder<TSub, Any>(IS_A, commonFields)
    .cast()

/**
 * Makes the assertion that [IAssertionPlant.subject] *is a* [TSub] (the same type or a sub-type) and if so,
 * uses [createAssertions] which could create further assertions which are lazily evaluated at the end.
 *
 * @return This plant to support a fluent-style API.
 */
inline fun <reified TSub : Any> IAssertionPlant<Any>.isA(noinline createAssertions: IAssertionPlant<TSub>.() -> Unit): IAssertionPlant<TSub>
    = AtriumFactory.newDownCastBuilder<TSub, Any>(IS_A, commonFields)
    .withLazyAssertions(createAssertions)
    .cast()

/**
 * Creates an [IAssertionPlant] which immediately evaluates [IAssertion]s using the given [feature] as
 * [subject][IAssertionPlant.subject].
 *
 * @return An [IAssertionPlant] for the given [feature], using an [AtriumFactory.newFeatureAssertionChecker].
 *
 * @See IAtriumFactory.newCheckImmediately
 */
fun <T : Any, TFeature : Any> IAssertionPlant<T>.its(feature: KProperty0<TFeature>): IAssertionPlant<TFeature>
    = AtriumFactory.newCheckImmediately(createCommonFieldsForFeatureFactory(feature))

/**
 * Creates an [IAssertionPlant] which lazily evaluates [IAssertion]s using the given [feature] as
 * [subject][IAssertionPlant.subject].
 *
 * The given [createAssertions] function is called after the plant has been created. It could create
 * [IAssertion]s for the given [feature] which are lazily evaluated by the newly created [IAssertionPlant]
 * after the call to [createAssertions] is made.
 *
 * @return An [IAssertionPlant] for the given [feature], using an [AtriumFactory.newFeatureAssertionChecker].
 *
 * @throws AssertionError Might throw an [AssertionError] if an additionally created [IAssertion]s
 *         (by calling [createAssertions]) does not hold.
 *
 * @see [IAtriumFactory.newCheckLazily]
 */
fun <T : Any, TFeature : Any> IAssertionPlant<T>.its(feature: KProperty0<TFeature>, createAssertions: IAssertionPlant<TFeature>.() -> Unit): IAssertionPlant<TFeature>
    = AtriumFactory.newCheckLazily(createCommonFieldsForFeatureFactory(feature))
    .createAssertionsAndCheckThem(createAssertions)

/**
 * Creates an [IAssertionPlantNullable] using the given [feature] as [subject][IAssertionPlantNullable.subject].
 *
 * @return An [IAssertionPlant] for the given [feature], using an [AtriumFactory.newNullable].
 */
fun <T : Any, TFeature : Any?> IAssertionPlant<T>.its(feature: KProperty0<TFeature>): IAssertionPlantNullable<TFeature>
    = AtriumFactory.newNullable(createCommonFieldsForFeatureFactory(feature))

private fun <T : Any, TFeature : Any?> IAssertionPlant<T>.createCommonFieldsForFeatureFactory(feature: KProperty0<TFeature>)
    = IAssertionPlantWithCommonFields.CommonFields(Untranslatable(feature.name), feature.get(), AtriumFactory.newFeatureAssertionChecker(this))

enum class DescriptionNarrowingAssertion(override val value: String) : IEnTranslatable {
    IS_NOT_NULL("is not"),
    IS_A("is type or sub-type of"),
}
