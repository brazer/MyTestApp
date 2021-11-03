package com.salanevich.testapp.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT_PATTERN = "yyyy/MM/dd"

val dateFormatter = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US)

//2001-01-31T15:40:53Z
var dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)