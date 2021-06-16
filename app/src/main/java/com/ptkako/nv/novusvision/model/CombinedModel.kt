package com.ptkako.nv.novusvision.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CombinedModel(val casts: String = "", val content_rating: String = "",
                         val country: String = "", val duration: String = "",
                         val full_video_path: String? = null, val genres: String = "",
                         val is_series: Boolean = false, val language: String = "",
                         val movie_cover_photo: String = "", val movie_name: String = "",
                         val movie_photo: String = "", val overview: String = "",
                         val popularity: String = "", val production_year: String = "",
                         val series_id: Int? = null, val subtitle: String = "", val status_code: String = "",
                         val trailer_video_path: String = "") : Parcelable
