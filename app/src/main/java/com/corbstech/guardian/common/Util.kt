package com.corbstech.guardian.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.TemporalAdjusters

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

fun LocalDate.isThisWeek(): Boolean {
    val monday = LocalDate.now(ZoneId.systemDefault())
        .with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
    // is on or after Monday of this week.
    return this.isEqual(monday) || this.isAfter(monday)
}

fun LocalDate.isLastWeek(): Boolean {
    val monday = LocalDate.now(ZoneId.systemDefault())
        .with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
    val lastMonday = LocalDate.now(ZoneId.systemDefault())
        .with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusWeeks(1)
    // is on or after Monday of last week and before Monday of this week.
    return (this.isEqual(lastMonday) || this.isAfter(lastMonday)) && this.isBefore(monday)
}

fun LocalDate.isBeforeLastWeek(): Boolean {
    val lastMonday = LocalDate.now(ZoneId.systemDefault())
        .with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusWeeks(1)
    // is before Monday of last week.
    return this.isBefore(lastMonday)
}
