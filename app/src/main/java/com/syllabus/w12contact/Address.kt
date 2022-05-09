package com.syllabus.w12contact

data class Address(
  var street: String,
  var extern: String,
  var intern: String?,
  var colony: String?,
  var city: String,
  var state: String,
  var country: String,
  var postalCode: String
);