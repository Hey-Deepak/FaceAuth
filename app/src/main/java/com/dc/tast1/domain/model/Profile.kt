package com.dc.tast1.domain.model

import java.net.Inet4Address

data class Profile(
    var displayName: String = "",
    var mailId: String = "",
    var displayPhoto: String = "",
    var mobileNumber: String = "",
    val address: String = ""
    )
