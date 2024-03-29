package org.hikit.common.data.helper

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.inject.Singleton

@Singleton
class GsonBeanHelper {
    val gsonBuilder: Gson? = GsonBuilder().create()
}