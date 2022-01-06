package com.ptkako.nv.novusvision.model

data class HistoryModel(val movie_id: String = "", val user_id: String = "",
                        val finish: Long = 0 , val last_played_time: String = "",
                        val last_watch: String = "")
