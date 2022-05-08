package com.oratakashi.goodgame

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


class DateTest {
    @Test
    fun getDateLastMonth() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, -1)
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
        println(date)
    }
}