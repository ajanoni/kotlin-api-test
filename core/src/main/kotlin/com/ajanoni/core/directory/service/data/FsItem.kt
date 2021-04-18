package com.ajanoni.core.directory.service.data

import java.time.Instant

data class FsItem(val name: String, val lastModification: Instant, val size: Long)
