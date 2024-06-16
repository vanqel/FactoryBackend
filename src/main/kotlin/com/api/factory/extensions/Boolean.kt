package com.api.factory.extensions

import com.api.factory.config.RemoveOutput

fun Boolean.getResponse(): RemoveOutput = RemoveOutput(this)
