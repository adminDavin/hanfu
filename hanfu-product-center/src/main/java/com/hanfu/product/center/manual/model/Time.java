package com.hanfu.product.center.manual.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class Time {
	private LocalDateTime dayStart;
	private LocalDateTime dayEnd;
	private LocalDateTime yestdayStart;
	private LocalDateTime yestdayEnd;
	private LocalDateTime mouthStart;
	private LocalDateTime mouthEnd;
	private LocalDateTime lastMouthStart;
	private LocalDateTime lastMouthEnd;
	public LocalDateTime getDayStart() {
		dayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		return dayStart;
	}
	public LocalDateTime getDayEnd() {
		dayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		return dayEnd;
	}
	public LocalDateTime getYestdayStart() {
		yestdayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(-1);
		return yestdayStart;
	}
	public LocalDateTime getYestdayEnd() {
		yestdayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(-1);
		return yestdayEnd;
	}
	public LocalDateTime getMouthStart() {
		mouthStart = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN);
		return mouthStart;
	}
	public LocalDateTime getMouthEnd() {
		mouthEnd = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX);
		return mouthEnd;
	}
	public LocalDateTime getLastMouthStart() {
		lastMouthStart = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN)
				.plusMonths(-1);
		return lastMouthStart;
	}
	public LocalDateTime getLastMouthEnd() {
		lastMouthEnd = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX)
				.plusMonths(-1);
		return lastMouthEnd;
	}
}
